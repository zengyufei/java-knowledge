package M_utils;

import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Slf4j
public class MqHelper {

    private final static AtomicBoolean isInit = new AtomicBoolean(false);

    private final static Object initLock = new Object();

    private static MqConnectionConfig mqConnectionConfig;
    private static Map<MqConfig, MqChannelFactory> factoryMap = new ConcurrentHashMap<>();
    private static Map<MqConfig, MqConfig> mqConfigMap = new ConcurrentHashMap<>();
    private static Map<MqConfig, AMQP.BasicProperties> propertiesMap = new ConcurrentHashMap<>();

    // 构造方法私有化 防止直接通过类创建实例
    private MqHelper() {
    }

    public static synchronized void initArgs(String host, String username, String password, int port) {
        if (isInit.get()) {
            return;
        }
        synchronized (initLock) {
            if (isInit.get()) {
                return;
            }

            // 设置账号信息，用户名、密码、vhost
            mqConnectionConfig = new MqConnectionConfig();
            mqConnectionConfig.setHost(host);
            mqConnectionConfig.setUsername(username);
            mqConnectionConfig.setPassword(password);
            mqConnectionConfig.setPort(port);
            isInit.compareAndSet(false, true);
        }
    }

    /**
     * 发送消息
     */
    public static void sendMsg(MqConfig mqConfig, String msg,
                               BiConsumer<Channel, SendAction> sendFunc) throws Exception {

        final MqConfig newConfig = mqConfigMap.computeIfAbsent(mqConfig, k -> {
            final String changeName = mqConfig.getChangeName();
            String routingKey = mqConfig.getRoutingKey();
            Boolean durable = mqConfig.getDurable();
            Boolean isDelay = mqConfig.getIsDelay();
            ExchangeType exchangeType = mqConfig.getExchangeType();
            Long delayTime = mqConfig.getDelayTime();
            Boolean isAutoClose = mqConfig.getIsAutoClose();
            final DeadConfig deadConfig = mqConfig.getDeadConfig();

            if (exchangeType == null) {
                exchangeType = ExchangeType.direct;
            }
            if (durable == null) {
                durable = true;
            }
            if (isAutoClose == null) {
                isAutoClose = false;
            }
            if (StrUtil.isBlankOrUndefined(routingKey)) {
                routingKey = "#";
            }
            if (isDelay == null) {
                isDelay = false;
            }
            else {
                if (isDelay && delayTime == null) {
                    delayTime = 1000L;
                }
                if (isDelay && StrUtil.isBlankOrUndefined(changeName)) {
                    throw new NullPointerException("延迟队列, 交换机不能为空");
                }
            }


            return MqConfig.of()
                    .title(mqConfig.getTitle())
                    .changeName(changeName)
                    .queueName(mqConfig.getQueueName())
                    .routingKey(routingKey)
                    .durable(durable)
                    .ttl(mqConfig.getTtl())
                    .max(mqConfig.getMax())
                    .exchangeType(exchangeType)
                    .isAutoClose(isAutoClose)
                    .isDelay(isDelay)
                    .delayTime(delayTime)
                    .deadConfig(deadConfig)
                    .build();
        });

        sendMsg(newConfig, msg, null, sendFunc);
    }

    /**
     * 发送消息
     */
    private static void sendMsg(MqConfig newConfig, String msg, Integer expiration,
                                BiConsumer<Channel, SendAction> sendFunc) throws Exception {

        final String exchangeName = newConfig.getChangeName();
        String routingKey = newConfig.getRoutingKey();
        Boolean isDelay = newConfig.getIsDelay();
        Long delayTime = newConfig.getDelayTime();
        final String queueName = newConfig.getQueueName();
        newConfig.setExpiration(String.valueOf(expiration));

        final Channel sendChannel = getSendChannel(newConfig);

        final ConfirmListener confirmListener = new ConfirmListener() {
            // 消息正确到达 broker
            @Override
            public void handleAck(long deliveryTag, boolean multiple) {
                log.debug("已收到消息，标识：{}", deliveryTag);
                // 做一些其他处理
                sendFunc.accept(sendChannel, SendAction.SUCCESS);
            }

            // RabbitMQ 因为自身内部错误导致消息丢失，就会发送一条nack消息
            @Override
            public void handleNack(long deliveryTag, boolean multiple) {
                log.warn("未确认消息，标识：{}", deliveryTag);
                // 做一些其他处理，比如消息重发等
                sendFunc.accept(sendChannel, SendAction.MQ_FAIL);
            }
        };
//        sendChannel.clearConfirmListeners();
        sendChannel.addConfirmListener(confirmListener);
        System.out.println("send: " + sendChannel.hashCode());

        // 建立通讯信道
        try {
            final AMQP.BasicProperties properties = propertiesMap.computeIfAbsent(newConfig, k -> getBasicProperties(expiration, exchangeName, isDelay, delayTime));

            String firstName = getName(exchangeName, routingKey, isDelay, null);
            String secondName = getName(exchangeName, routingKey, isDelay, queueName);

            log.trace("系统发送发送消息前记录: {} {}", sendChannel.getChannelNumber(), msg);
            sendChannel.basicPublish(firstName, secondName, properties, msg.getBytes(StandardCharsets.UTF_8));
            log.trace("系统发送发送消息: {} {}", sendChannel.getChannelNumber(), msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 消费消息
     *
     * @param successMessage 消息处理函数
     */
    public static void consumeMsg(MqConfig mqConfig, BiFunction<Channel, String, ConsumeAction> successMessage)
            throws Exception {
        ExchangeType exchangeType = mqConfig.getExchangeType();
        Boolean durable = mqConfig.getDurable();
        String routingKey = mqConfig.getRoutingKey();
        Boolean isAutoClose = mqConfig.getIsAutoClose();
        final String changeName = mqConfig.getChangeName();
        final String queueName = mqConfig.getQueueName();
        final DeadConfig deadConfig = mqConfig.getDeadConfig();

        if (exchangeType == null) {
            exchangeType = ExchangeType.direct;
        }
        if (durable == null) {
            durable = true;
        }
        if (isAutoClose == null) {
            isAutoClose = false;
        }
        if (StrUtil.isBlankOrUndefined(routingKey)) {
            routingKey = "#";
        }

        final MqConfig newConfig = MqConfig.of()
                .title(mqConfig.getTitle())
                .changeName(changeName)
                .queueName(mqConfig.getQueueName())
                .routingKey(routingKey)
                .durable(durable)
                .ttl(mqConfig.getTtl())
                .max(mqConfig.getMax())
                .exchangeType(exchangeType)
                .isAutoClose(isAutoClose)
                .deadConfig(deadConfig)
                .build();

        handler(successMessage, queueName, newConfig);
    }

    private static void handler(BiFunction<Channel, String, ConsumeAction> successMessage, String queueName, MqConfig newConfig) throws Exception {
//        final Channel consumeChannel = getConsumeChannel(newConfig);
        newConfig.setType("1");
        final MqChannelFactory mqChannelFactory = getMqChannelFactory(newConfig);
//        final Connection connection = mqChannelFactory.getConnection();
        final Channel consumeChannel = mqChannelFactory.create();
        // 建立消费者
//            Consumer consumer = new DefaultConsumer(consumeChannel) {
//
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
//                                           byte[] body) {
//                    long deliveryTag = envelope.getDeliveryTag();
//                    ConsumeAction consumeAction;
//                    try {
//                        String message = new String(body, StandardCharsets.UTF_8);
//                        consumeAction = successMessage.apply(consumeChannel, message);
//                        // 消息丢到死信 或 从死信直接丢弃
//                        if (consumeAction == ConsumeAction.ACCEPT) {
//                            consumeChannel.basicAck(deliveryTag, false);
//                        }
//                        else {
//                            consumeChannel.basicNack(deliveryTag, false, consumeAction == ConsumeAction.RETRY);
//                        }
//                    } catch (Exception e) {
//                        log.error("异常: {}", ExceptionUtil.stacktraceToString(e));
////                        throw new RuntimeException(e);
//                    }
//                }
//            };
        // 从左到右参数意思分别是：队列名称、是否自动确认，消费者
        consumeChannel.basicConsume(queueName, false, new CustomConsumer(mqChannelFactory, consumeChannel, queueName, successMessage));
    }

    private static String getName(String exchangeName, String routingKey, Boolean isDelay, String queueName) {
        boolean isFirstName = StrUtil.isBlank(queueName);

        String name;
        if (isDelay) {
            name = isFirstName ? exchangeName : routingKey;
        }
        else {
            if (StrUtil.isNotBlank(exchangeName)) {
                name = isFirstName ? exchangeName : routingKey;
            }
            else {
                name = isFirstName ? routingKey : queueName;
            }
        }
        return name;
    }

    private static AMQP.BasicProperties getBasicProperties(Integer expiration, String exchangeName, Boolean isDelay, Long delayTime) {
        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder();
        props.deliveryMode(2); // message持久化
        if (expiration != null) {
            props.expiration(expiration.toString());
        }
        if (isDelay) {
            Map<String, Object> headers = new HashMap<>();
            headers.put("x-delay", delayTime);
            props.headers(headers);
        }
        else {
            if (!StrUtil.isNotBlank(exchangeName)) {
                props.priority(null);
            }
        }
        final AMQP.BasicProperties properties = props.build();
        return properties;
    }

    private static Channel getSendChannel(MqConfig mqConfig) throws Exception {
        mqConfig.setType("0");
        return getChannel(mqConfig);
    }

    private static Channel getConsumeChannel(MqConfig mqConfig) throws Exception {
        mqConfig.setType("1");
        return getChannel(mqConfig);
    }

    private static Channel getChannel(MqConfig mqConfig) throws Exception {
        final MqChannelFactory factory = getMqChannelFactory(mqConfig);
        return factory.create();
//        return channelMap.computeIfAbsent(mqConfig, k -> {
//            try {
//                return factory.create();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    public static MqChannelFactory getMqChannelFactory(MqConfig mqConfig) {
        final MqChannelFactory factory = factoryMap.computeIfAbsent(mqConfig, k -> {
            try {
                return new MqChannelFactory(mqConnectionConfig, mqConfig);
            } catch (IOException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        });
        return factory;
    }


    public static Connection getConnection(MqConfig mqConfig) {
        return getMqChannelFactory(mqConfig).getConnection();
    }


    /**
     * 获取消息数
     *
     * @return int
     * @throws IOException      io异常
     * @throws TimeoutException 超时异常
     */
    public static int getMessageCount(MqConfig mqConfig) throws Exception {
        ExchangeType exchangeType = mqConfig.getExchangeType();
        Boolean durable = mqConfig.getDurable();
        String routingKey = mqConfig.getRoutingKey();
        Boolean isAutoClose = mqConfig.getIsAutoClose();
        final String changeName = mqConfig.getChangeName();
        final String queueName = mqConfig.getQueueName();
        final DeadConfig deadConfig = mqConfig.getDeadConfig();

        if (exchangeType == null) {
            exchangeType = ExchangeType.direct;
        }
        if (durable == null) {
            durable = true;
        }
        if (isAutoClose == null) {
            isAutoClose = false;
        }
        if (StrUtil.isBlankOrUndefined(routingKey)) {
            routingKey = "#";
        }

        final MqConfig newConfig = MqConfig.of()
                .title(mqConfig.getTitle())
                .changeName(changeName)
                .queueName(mqConfig.getQueueName())
                .routingKey(routingKey)
                .durable(durable)
                .ttl(mqConfig.getTtl())
                .max(mqConfig.getMax())
                .exchangeType(exchangeType)
                .isAutoClose(isAutoClose)
                .deadConfig(deadConfig)
                .build();
        final Channel channel = getConsumeChannel(newConfig);
        int msgCount = 0;
        channel.queueDeclare(queueName, true, false, false, null); //获取队列
        msgCount = (int) channel.messageCount(queueName);
        return msgCount;
    }

}

package M_utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@Data
public class MqChannelFactory {

    private final MqConfig mqConfig;
    private final List<Channel> channels = new ArrayList<>();
    private MqConnectionConfig mqConnectionConfig;
    private Connection connection;

    public MqChannelFactory(MqConnectionConfig mqConnectionConfig, MqConfig mqConfig)
            throws IOException, TimeoutException {
        super();
        this.mqConnectionConfig = mqConnectionConfig;
        this.mqConfig = mqConfig;

        connect();
    }

    private void connect() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置服务地址
        connectionFactory.setHost(mqConnectionConfig.getHost());
        // 设置账号信息，用户名、密码、vhost
        connectionFactory.setUsername(mqConnectionConfig.getUsername());
        connectionFactory.setPassword(mqConnectionConfig.getPassword());
        connectionFactory.setPort(mqConnectionConfig.getPort());
        connectionFactory.setAutomaticRecoveryEnabled(true); // 自动重连
        connectionFactory.setNetworkRecoveryInterval(10000);// 10秒
        connectionFactory.setTopologyRecoveryEnabled(true);
        // 基于网络环境设置合理的超时时间。
        connectionFactory.setConnectionTimeout(30 * 1000);
        connectionFactory.setHandshakeTimeout(30 * 1000);
        connectionFactory.setChannelRpcTimeout(30 * 1000);
        connectionFactory.setRequestedHeartbeat(30); // 设置心跳为30秒
        connectionFactory.setShutdownTimeout(0);

        connection = connectionFactory.newConnection();
    }

    public Channel resetChannel() {
        reConnect();
        return resetCreateChannel();
    }

    public void reConnect() {
        try {
            closeResources(); // 关闭当前连接
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        try {
            connect(); // 尝试重新连接
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    private void closeResources() {
        closeChannels();
        closeConnection();
    }

    private Channel resetCreateChannel() {
        while (true) {
            try {
                if (connection == null) {
                    connect(); // 尝试重新连接
                }
                return createChannel();
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
    }

    private void closeConnection() {
        try {
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            connection = null;
        }
    }

    private void closeChannels() {
        try {
            for (Channel channel : channels) {
                if (channel != null && channel.isOpen()) {
                    channel.close();
                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            channels.clear();
        }
    }

    public Channel createChannel() throws Exception {
        log.trace("在对象池中创建 Channel 对象");
        final Channel channel = getChannel();

        if (mqConfig.getType().equals("0")) {
            final String changeName = mqConfig.getChangeName();
            final String queueName = mqConfig.getQueueName();
            final String routingKey = mqConfig.getRoutingKey();
            final Boolean durable = mqConfig.getDurable();
            final Boolean isDelay = mqConfig.getIsDelay();
            final BuiltinExchangeType exchangeType = mqConfig.getExchangeType();
            final Long ttl = mqConfig.getTtl();
            final Long max = mqConfig.getMax();
            final MqConfig.DeadConfig deadConfig = mqConfig.getDeadConfig();

            // 注意，因为要等待broker的confirm消息，暂时不关闭channel和connection
            Map<String, Object> args = new HashMap<>();
            // 队列设置最大长度
            if (max != null) {
                args.put("x-max-length", max);
            }
            // 设置队列有效期为10秒
            if (ttl != null) {
                args.put("x-message-ttl", ttl);
            }
            if (deadConfig != null) {
                final String deadChangeName = deadConfig.getChangeName();
                String deadRoutingKey = deadConfig.getRoutingKey();
                if (StrUtil.isBlankOrUndefined(deadRoutingKey)) {
                    deadRoutingKey = "#";
                }
                args.put("x-dead-letter-exchange", deadChangeName);
                args.put("x-dead-letter-routing-key", deadRoutingKey);
            }

            if (isDelay) {
                // 延迟交换器 https://xie.infoq.cn/article/e0c56c9d10a047fb179bc3aba
                args.put("x-delayed-type", exchangeType);
                // exchange 持久化
                channel.exchangeDeclare(changeName, "x-delayed-message", durable, false, args);
                // 绑定路由
                channel.queueBind(queueName, changeName, routingKey);
            }
            else if (StrUtil.isNotBlank(changeName)) {
                /*
                 * 声明一个交换机 参数1：交换机的名称，取值任意 参数2：交换机的类型，取值为：direct、fanout、topic、headers
                 * 参数3：是否为持久化的交换机 注意： 1) 声明交换机时，如果这个交换机已经存在，则放弃声明；如果交换机不存在，则声明该交换机 2)
                 * 这行代码可有可无，但是使用前要确保该交换机已存在
                 */
                channel.exchangeDeclare(changeName, exchangeType, durable, false, args);
                /*
                 * 将队列绑定到交换机 参数1：队列的名称 参数2：交换机的名称 参数3：消息的RoutingKey（就是BindingKey） 注意： 1)
                 * 在进行队列和交换机绑定时，必须要确保交换机和队列已经成功声明
                 */
                channel.queueBind(queueName, changeName, routingKey);
            }

            // 参数从前面开始分别意思为：队列名称，是否持久化，独占的队列，不使用时是否自动删除，其他参数
            channel.queueDeclare(queueName, durable, false, false, args);
            channel.confirmSelect();// 开启发送方确认模式
        }
        else {
            final String changeName = mqConfig.getChangeName();
            final String queueName = mqConfig.getQueueName();
            final String routingKey = mqConfig.getRoutingKey();
            final Boolean durable = mqConfig.getDurable();
            final Boolean isDelay = mqConfig.getIsDelay();
            final BuiltinExchangeType exchangeType = mqConfig.getExchangeType();
            final Long ttl = mqConfig.getTtl();
            final Long max = mqConfig.getMax();
            final MqConfig.DeadConfig deadConfig = mqConfig.getDeadConfig();

            Map<String, Object> args = new HashMap<>();
            if (deadConfig != null) {
                final String deadChangeName = deadConfig.getChangeName();
                final String deadQueueName = deadConfig.getQueueName();
                String deadRoutingKey = deadConfig.getRoutingKey();
                if (StrUtil.isBlankOrUndefined(deadRoutingKey)) {
                    deadRoutingKey = "#";
                }

                channel.queueDeclare(deadQueueName, durable, false, false, args);

                args.put("x-dead-letter-exchange", deadChangeName);
                args.put("x-dead-letter-routing-key", deadRoutingKey);

                if (!StrUtil.isBlankOrUndefined(deadChangeName)) {
                    channel.exchangeDeclare(deadChangeName, exchangeType, durable);
                    channel.queueBind(deadQueueName, deadChangeName, deadRoutingKey);
                }
            }

            channel.queueDeclare(queueName, durable, false, false, args);
            if (StrUtil.isNotBlank(changeName)) {
                /*
                 * 声明一个交换机 参数1：交换机的名称，取值任意 参数2：交换机的类型，取值为：direct、fanout、topic、headers
                 * 参数3：是否为持久化的交换机 注意： 1) 声明交换机时，如果这个交换机已经存在，则放弃声明；如果交换机不存在，则声明该交换机 2)
                 * 这行代码可有可无，但是使用前要确保该交换机已存在
                 */
                channel.exchangeDeclare(changeName, exchangeType, durable);
                // 绑定路由
                channel.queueBind(queueName, changeName, routingKey);
            }
            // prefetchSize 和global这两项，实际过程中几乎不使用
            // prefetchSize：可接收消息的大小的
            // prefetchCount：处理消息最大的数量
            // global：是不是针对整个Connection的，因为一个Connection可以有多个Channel，如果是false则说明只是针对于这个Channel的
            channel.basicQos(0, 1, false); // 分发机制为触发式
        }

        channels.add(channel);

        return channel;
    }

    private Channel getChannel() throws Exception {
        return connection.createChannel();
    }

}

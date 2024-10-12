package M_utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.rabbitmq.client.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;

@Slf4j
@Data
public class CustomConsumer implements Consumer {
    private Channel channel;
    private String queueName;
    private final MqChannelFactory mqChannelFactory;
    private final BiFunction<Channel, String, ConsumeAction> successMessage;

    public CustomConsumer(final MqChannelFactory mqChannelFactory, Channel channel, String queueName, BiFunction<Channel, String, ConsumeAction> successMessage) {
        this.channel = channel;
        this.mqChannelFactory = mqChannelFactory;
        this.queueName = queueName;
        this.successMessage = successMessage;
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
    }

    @Override
    public void handleCancelOk(String consumerTag) {
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        try {
            this.mqChannelFactory.reconnect();
            this.channel = this.mqChannelFactory.create();
            channel.basicConsume(queueName, false, this);
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        try {
            this.mqChannelFactory.reconnect();
            this.channel = this.mqChannelFactory.create();
            channel.basicConsume(queueName, false, this);
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                               byte[] body) {
        long deliveryTag = envelope.getDeliveryTag();
        ConsumeAction consumeAction;
        try {
            String message = new String(body, StandardCharsets.UTF_8);
            consumeAction = successMessage.apply(channel, message);
            // 消息丢到死信 或 从死信直接丢弃
            if (consumeAction == ConsumeAction.ACCEPT) {
                channel.basicAck(deliveryTag, false);
            }
            else {
                channel.basicNack(deliveryTag, false, consumeAction == ConsumeAction.RETRY);
            }
        } catch (Exception e) {
            log.error("异常: {}", ExceptionUtil.stacktraceToString(e));
//                        throw new RuntimeException(e);
        }
    }
}

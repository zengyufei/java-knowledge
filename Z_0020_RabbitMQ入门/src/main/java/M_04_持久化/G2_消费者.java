package M_04_持久化;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class G2_消费者 {

    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() {
        ConnectionFactory 连接工厂 = new ConnectionFactory();
        连接工厂.setHost(G0_常量.HOST);
        连接工厂.setPort(G0_常量.端口);
        连接工厂.setUsername(G0_常量.账号);
        连接工厂.setPassword(G0_常量.密码);

        Connection 连接 = null;
        Channel 信道 = null;

        try {
            连接 = 连接工厂.newConnection();
            信道 = 连接.createChannel();

            /**
             * 接收消息
             * @param queue             队列名称
             * @param autoAck           消费成功后是否自动应答 true-是 false-否
             * @param deliverCallback   接收消息后的回调
             * @param cancelCallback    取消消息后的回调
             */
            // 接收消息,消息改为手动确认
            boolean autoAck = false;
            Channel final信道 = 信道;
            信道.basicConsume(G0_常量.队列名称, autoAck, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    System.out.println("接收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8));

                    final信道.basicAck(message.getEnvelope().getDeliveryTag(), false);

                    System.exit(0);
                }
            }, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {
                    System.out.println("消息消费被中断,取消消费消息");
                }
            });
            // 对消息进行阻断不在往下运行
            System.in.read();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (连接 != null && 连接.isOpen()) {
                try {
                    连接.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (信道 != null && 信道.isOpen()) {
                try {
                    信道.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        监听队列接收mq消息();
    }
}

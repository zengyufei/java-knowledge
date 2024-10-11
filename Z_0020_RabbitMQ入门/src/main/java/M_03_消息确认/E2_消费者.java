package M_03_消息确认;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class E2_消费者 {

    static boolean init = false;

    static Channel 信道;

    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() {
        if (!init) {
            信道 = E0_创建信道.创建信道();
        }

        try {

            /**
             * 接收消息
             * @param queue             队列名称
             * @param autoAck           消费成功后是否自动应答 true-是 false-否
             * @param deliverCallback   接收消息后的回调
             * @param cancelCallback    取消消息后的回调
             */
            // 接收消息,消息改为手动确认
            boolean autoAck = false;
            信道.basicConsume(E0_常量.队列名称, autoAck,
                    (consumerTag, message) -> {
                        System.out.println(E2_消费者.class.getSimpleName() + " =" + 信道.hashCode());
                        final String val = new String(message.getBody(), StandardCharsets.UTF_8);
                        System.out.println(E2_消费者.class.getSimpleName() + "接收到消息: " + val);
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        /**
                         * 消息手动确认
                         * 消息的标志 tag
                         * 是否批量确认 true-是 false-否
                         */
                        System.out.println(E2_消费者.class.getSimpleName() + "消费完毕: " + val);
                        信道.basicAck(message.getEnvelope().getDeliveryTag(), false);
                    },
                    consumerTag -> System.out.println(E2_消费者.class.getSimpleName() + "消息消费被中断,取消消费消息"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        监听队列接收mq消息();
    }


    public static void 关闭() {
        // 9.关闭信道
        if (信道 != null && 信道.isOpen()) {
            try {
                System.out.println(E2_消费者.class.getSimpleName() + " 关闭=" + 信道.hashCode());
                信道.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        final Connection 连接 = 信道.getConnection();
        // 10.关闭连接
        if (连接 != null && 连接.isOpen()) {
            try {
                连接.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

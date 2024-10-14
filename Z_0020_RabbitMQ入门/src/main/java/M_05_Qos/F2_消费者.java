package M_05_Qos;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class F2_消费者 {

    static boolean init = false;

    static Channel 信道;

    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() {
        if (!init) {
            信道 = F0_创建信道.创建信道();
        }

        try {

            // prefetchSize 和global这两项，实际过程中几乎不使用
            // prefetchSize：可接收消息的大小的
            // prefetchCount：处理消息最大的数量
            // global：是不是针对整个Connection的，因为一个Connection可以有多个Channel，如果是false则说明只是针对于这个Channel的
            // 设置 qos 为 1
            信道.basicQos(1);
            /**
             * 接收消息
             * @param queue             队列名称
             * @param autoAck           消费成功后是否自动应答 true-是 false-否
             * @param deliverCallback   接收消息后的回调
             * @param cancelCallback    取消消息后的回调
             */
            // 接收消息,消息改为手动确认
            boolean autoAck = false;
            信道.basicConsume(F0_常量.队列名称, autoAck,
                    (consumerTag, message) -> {
                        Console.log(F2_消费者.class.getSimpleName() + " =" + 信道.hashCode());
                        final String val = new String(message.getBody(), StandardCharsets.UTF_8);
                        Console.log(F2_消费者.class.getSimpleName() + "接收到消息: " + val);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        /**
                         * 消息手动确认
                         * 消息的标志 tag
                         * 是否批量确认 true-是 false-否
                         */
                        Console.log(F2_消费者.class.getSimpleName() + "消费完毕: " + val);
                        信道.basicAck(message.getEnvelope().getDeliveryTag(), false);
                    },
                    consumerTag -> Console.log(F2_消费者.class.getSimpleName() + "消息消费被中断,取消消费消息"));
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
                Console.log(F2_消费者.class.getSimpleName() + " 关闭=" + 信道.hashCode());
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

package M_03_消息确认;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class E3_消费者 {

    static boolean init = false;

    static Channel 信道;

    static AtomicBoolean 标识 = new AtomicBoolean();
    static volatile AtomicInteger 计数 = new AtomicInteger();

    /**
     * 消费消息
     */
    public static String 监听队列接收mq消息() {
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
            final String basicConsume = 信道.basicConsume(E0_常量.队列名称, autoAck,
                    (consumerTag, message) -> {
                        final String val = new String(message.getBody(), StandardCharsets.UTF_8);
                        Console.log(E3_消费者.class.getSimpleName() + "接收到消息: " + val);
                        if (计数.get() > 0) {
                            System.exit(0);
                        }
                        /**
                         * 消息手动确认
                         * 消息的标志 tag
                         * 是否批量确认 true-是 false-否
                         */
                        Console.log(E3_消费者.class.getSimpleName() + "消费完毕: " + val);
                        计数.incrementAndGet();
                        信道.basicAck(message.getEnvelope().getDeliveryTag(), false);
                    },
                    consumerTag -> Console.log(E3_消费者.class.getSimpleName() + "消息消费被中断,取消消费消息"));
            return basicConsume;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        监听队列接收mq消息();
    }


}

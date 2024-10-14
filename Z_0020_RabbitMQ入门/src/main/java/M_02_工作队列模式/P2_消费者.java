package M_02_工作队列模式;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class P2_消费者 {


    static boolean init = false;

    static Channel 信道;


    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() {
        if (!init) {
            信道 = P0_创建信道.创建信道();
        }

        try {

            /**
             * 接收消息
             * @param queue             队列名称
             * @param autoAck           消费成功后是否自动应答 true-是 false-否
             * @param deliverCallback   接收消息后的回调
             * @param cancelCallback    取消消息后的回调
             */
            信道.basicConsume(P0_常量.队列名称, true,
                    (consumerTag, message) -> Console.log(P2_消费者.class.getSimpleName() + "接收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8)),
                    consumerTag -> Console.log(P2_消费者.class.getSimpleName() + "消息消费被中断,取消消费消息"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // 接收消息
        信道.basicConsume(P0_常量.队列名称, true, (customerTag, message) -> {
            Console.log(P2_消费者.class.getSimpleName() + "接收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8));
        }, (customerTag) -> {
            Console.log("消息消费被中断,取消消费消息");
        });
    }


    public static void 关闭() {
        // 9.关闭信道
        if (信道 != null && 信道.isOpen()) {
            try {
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

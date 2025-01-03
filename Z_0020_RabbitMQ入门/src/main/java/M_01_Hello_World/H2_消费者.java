package M_01_Hello_World;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class H2_消费者 {
    static final String simpleName = H2_消费者.class.getSimpleName();

    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() throws IOException, TimeoutException {
        Channel 信道 = 创建信道();

        /**
         * 接收消息
         * @param queue             队列名称
         * @param autoAck           消费成功后是否自动应答 true-是 false-否
         * @param deliverCallback   接收消息后的回调
         * @param cancelCallback    取消消息后的回调
         */
        信道.basicConsume(H0_常量.队列名称, true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                Console.log("接收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8));
                System.exit(0);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                Console.log("消息消费被中断,取消消费消息");
            }
        });
        // 对消息进行阻断不在往下运行
        System.in.read();
    }

    private static Channel 创建信道() throws IOException, TimeoutException {
        ConnectionFactory 连接工厂 = new ConnectionFactory();
        连接工厂.setHost(H0_常量.HOST);
        连接工厂.setPort(H0_常量.端口);
        连接工厂.setUsername(H0_常量.账号);
        连接工厂.setPassword(H0_常量.密码);

        Connection 连接 = 连接工厂.newConnection();
        Channel 信道 = 连接.createChannel();
        return 信道;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        监听队列接收mq消息();
    }
}

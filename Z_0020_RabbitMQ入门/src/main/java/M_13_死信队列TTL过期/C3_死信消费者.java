package M_13_死信队列TTL过期;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class C3_死信消费者 {

    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() throws IOException, TimeoutException {
        Channel 信道 = 创建信道();

        信道.basicConsume(C0_常量.死信队列名称, true,
                (consumerTag, message) -> Console.log("死信队列名称接收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8)),
                consumerTag -> Console.log("死信队列名称消息消费被中断,取消消费消息"));
        System.out.println("等待接收消息......");
        // 对消息进行阻断不在往下运行
        System.in.read();

    }

    private static Channel 创建信道() throws IOException, TimeoutException {
        ConnectionFactory 连接工厂 = new ConnectionFactory();
        连接工厂.setHost(C0_常量.HOST);
        连接工厂.setPort(C0_常量.端口);
        连接工厂.setUsername(C0_常量.账号);
        连接工厂.setPassword(C0_常量.密码);

        Connection 连接 = 连接工厂.newConnection();
        Channel 信道 = 连接.createChannel();
        return 信道;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        监听队列接收mq消息();
    }
}

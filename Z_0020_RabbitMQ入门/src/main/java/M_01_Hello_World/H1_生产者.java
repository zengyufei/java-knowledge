package M_01_Hello_World;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class H1_生产者 {
    static final String simpleName = H1_生产者.class.getSimpleName();

    public static void 生产消息发送mq(String 消息内容) throws IOException, TimeoutException {
        Channel 信道 = 创建信道();

        // 7.声明队列
        /**
         * @param queue         队列名称
         * @param durable       是否持久化durable=false(默认) 所谓持久化消息是否存储到磁盘,如果false非持久化,true是持久化
         * @param exclusive     队列是否只提供一个消费者消费,是否进行消息共享,true-允许多个消费者消费 false-不允许
         * @param autoDelete    是否自动删除 最后一个消费者断开连接之后，该队列是否自动删除 true-自动删除 false-不自动删除
         * @param arguments     其他参数
         */
        信道.queueDeclare(H0_常量.队列名称, false, false, false, null);

        // 8.发送消息
        /**
         * @param exchange 交换机名称
         * @param routingKey 路由key,本次是队列名称
         * @param props 其他属性
         * @param body 消息体
         */
        信道.basicPublish("", H0_常量.队列名称, null, 消息内容.getBytes(StandardCharsets.UTF_8));
        Console.log("{}消息发送成功!消息: {}", simpleName, 消息内容);
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
        String 消息 = "hello world";
        生产消息发送mq(消息);
    }
}

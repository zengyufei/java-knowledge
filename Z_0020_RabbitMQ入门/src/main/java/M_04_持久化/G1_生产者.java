package M_04_持久化;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class G1_生产者 {

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
        // 设置队列持久化
        boolean durable = true;
        信道.queueDeclare(G0_常量.队列名称, durable, false, false, null);

        // 8.发送消息
        /**
         * @param exchange 交换机名称
         * @param routingKey 路由key,本次是队列名称
         * @param props 其他属性
         * @param body 消息体
         */
        // 设置生产者发送消息为持久化消息（即保存在磁盘上）
        final AMQP.BasicProperties properties = MessageProperties.PERSISTENT_TEXT_PLAIN;
        信道.basicPublish("", G0_常量.队列名称, properties, 消息内容.getBytes(StandardCharsets.UTF_8));
        Console.log("消息发送成功!");
    }

    private static Channel 创建信道() throws IOException, TimeoutException {
        ConnectionFactory 连接工厂 = new ConnectionFactory();
        连接工厂.setHost(G0_常量.HOST);
        连接工厂.setPort(G0_常量.端口);
        连接工厂.setUsername(G0_常量.账号);
        连接工厂.setPassword(G0_常量.密码);

        Connection 连接 = 连接工厂.newConnection();
        Channel 信道 = 连接.createChannel();
        return 信道;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        String 消息 = "hello world";
        生产消息发送mq(消息);
    }
}

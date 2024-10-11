package M_01_Hello_World;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class H1_生产者 {

    public static void 生产消息发送mq(String 消息内容) {
        // 1.创建连接工厂
        ConnectionFactory 连接工厂 = new ConnectionFactory();

        // 2.设置IP
        连接工厂.setHost(H0_常量.HOST);

        // 3.设置端口,注意端口不是web界面访问的端口
        连接工厂.setPort(H0_常量.端口);

        // 4.设置用户名和密码
        连接工厂.setUsername(H0_常量.账号);
        连接工厂.setPassword(H0_常量.密码);

        Connection 连接 = null;
        Channel 信道 = null;

        try {
            // 5.创建连接
            连接 = 连接工厂.newConnection();

            // 6.创建信道
            信道 = 连接.createChannel();

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
            System.out.println("消息发送成功!");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 9.关闭连接
            if (连接 != null && 连接.isOpen()) {
                try {
                    连接.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 10.关闭信道
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
        String 消息 = "hello world";
        生产消息发送mq(消息);
    }
}

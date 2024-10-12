package M_02_工作队列模式;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class P1_生产者 {

    static Channel 信道;

    static {
        信道 = P0_创建信道.创建信道();
    }

    public static void 生产消息发送mq(String 消息内容) {
        try {
            // 7.声明队列
            /**
             * @param queue         队列名称
             * @param durable       是否持久化durable=false(默认) 所谓持久化消息是否存储到磁盘,如果false非持久化,true是持久化
             * @param exclusive     队列是否只提供一个消费者消费,是否进行消息共享,true-允许多个消费者消费 false-不允许
             * @param autoDelete    是否自动删除 最后一个消费者断开连接之后，该队列是否自动删除 true-自动删除 false-不自动删除
             * @param arguments     其他参数
             */
            信道.queueDeclare(P0_常量.队列名称, false, false, false, null);

            // 8.发送消息
            /**
             * @param exchange 交换机名称
             * @param routingKey 路由key,本次是队列名称
             * @param props 其他属性
             * @param body 消息体
             */
            信道.basicPublish("", P0_常量.队列名称, null, 消息内容.getBytes(StandardCharsets.UTF_8));
            Console.log("消息发送成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        String 消息 = "hello world";

        // 声明队列
        信道.queueDeclare(P0_常量.队列名称, false, false, false, null);

        // 从控制台接收消息
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String message = scanner.next();

            // 发送消息
            信道.basicPublish("", P0_常量.队列名称, null, message.getBytes(StandardCharsets.UTF_8));

            Console.log("发送消息: " + message);
        }
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

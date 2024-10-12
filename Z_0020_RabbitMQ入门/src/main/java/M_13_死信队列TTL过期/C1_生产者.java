package M_13_死信队列TTL过期;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class C1_生产者 {


    public static void 生产消息发送mq() throws IOException, TimeoutException, InterruptedException {
        final Channel 信道 = 创建信道();

        // 死信消息  设置TTL
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder().expiration("1000").build();

        // 8.发送消息
        /**
         * @param exchange 交换机名称
         * @param routingKey 路由key
         * @param props 其他属性
         * @param body 消息体
         */
        for (int i = 0; i < 5; i++) {
            final String 消息内容 = "work " + i  + " " + DateUtil.current();
            信道.basicPublish(C0_常量.普通交换机名称, C0_常量.普通路由键, properties, 消息内容.getBytes(StandardCharsets.UTF_8));
            Console.log("交换机: {} 消息发送成功!", C0_常量.普通交换机名称);
        }
        System.exit(0);
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

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        生产消息发送mq();
        System.exit(0);
    }
}

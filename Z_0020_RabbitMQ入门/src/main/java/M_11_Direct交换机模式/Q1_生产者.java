package M_11_Direct交换机模式;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Q1_生产者 {


    public static void 生产消息发送mq(String 消息内容) throws IOException, TimeoutException {
        final Channel 信道 = 创建信道();

        /**
         * 声明交换机
         * 1.交换机的名称
         * 2.交换机的类型
         */
        信道.exchangeDeclare(Q0_常量.交换机名称, BuiltinExchangeType.DIRECT);

        // 8.发送消息
        /**
         * @param exchange 交换机名称
         * @param routingKey 路由key
         * @param props 其他属性
         * @param body 消息体
         */
        final int nextInt = new Random().nextInt(0, 2);
        final String 路由键 = Q0_常量.路由键数组[nextInt];
        信道.basicPublish(Q0_常量.交换机名称, 路由键, null, 消息内容.getBytes(StandardCharsets.UTF_8));
        Console.log("交换机: {} 路由键: {} 消息发送成功!", Q0_常量.交换机名称, 路由键);
    }


    private static Channel 创建信道() throws IOException, TimeoutException {
        ConnectionFactory 连接工厂 = new ConnectionFactory();
        连接工厂.setHost(Q0_常量.HOST);
        连接工厂.setPort(Q0_常量.端口);
        连接工厂.setUsername(Q0_常量.账号);
        连接工厂.setPassword(Q0_常量.密码);

        Connection 连接 = 连接工厂.newConnection();
        Channel 信道 = 连接.createChannel();
        return 信道;
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            生产消息发送mq("work " + i + DateUtil.current());
            TimeUnit.SECONDS.sleep(1);
        }
        System.exit(0);
    }
}

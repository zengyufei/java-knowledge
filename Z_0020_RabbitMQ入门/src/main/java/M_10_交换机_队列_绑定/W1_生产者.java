package M_10_交换机_队列_绑定;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class W1_生产者 {
   static final String simpleName = W1_生产者.class.getSimpleName();

    public static void 生产消息发送mq(String 消息内容) throws IOException, TimeoutException {
        Channel 信道 = 创建信道();

        /**
         * 声明交换机
         * 1.交换机的名称
         * 2.交换机的类型
         */
        信道.exchangeDeclare(W0_常量.交换机名称, BuiltinExchangeType.FANOUT);

        // 8.发送消息
        /**
         * @param exchange 交换机名称
         * @param routingKey 路由key
         * @param props 其他属性
         * @param body 消息体
         */
        信道.basicPublish(W0_常量.交换机名称, W0_常量.路由键, null, 消息内容.getBytes(StandardCharsets.UTF_8));
        Console.log("{}消息发送成功!消息: {}", simpleName, 消息内容);
    }


    private static Channel 创建信道() throws IOException, TimeoutException {
        ConnectionFactory 连接工厂 = new ConnectionFactory();
        连接工厂.setHost(W0_常量.HOST);
        连接工厂.setPort(W0_常量.端口);
        连接工厂.setUsername(W0_常量.账号);
        连接工厂.setPassword(W0_常量.密码);

        Connection 连接 = 连接工厂.newConnection();
        Channel 信道 = 连接.createChannel();
        return 信道;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        String 消息 = "hello world "  + DateUtil.current();
        生产消息发送mq(消息);
        System.exit(0);
    }
}

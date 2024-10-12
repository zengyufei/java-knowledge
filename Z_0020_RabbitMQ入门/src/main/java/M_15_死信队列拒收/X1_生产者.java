package M_15_死信队列拒收;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class X1_生产者 {

    public static void 生产消息发送mq() throws IOException, TimeoutException, InterruptedException {
        final Channel 信道 = 创建信道();

        // 8.发送消息
        /**
         * @param exchange 交换机名称
         * @param routingKey 路由key
         * @param props 其他属性
         * @param body 消息体
         */
        for (int i = 0; i < 5; i++) {
            final String 消息内容 = "work " + i + " " + DateUtil.current();
            信道.basicPublish(X0_常量.普通交换机名称, X0_常量.普通路由键, null, 消息内容.getBytes(StandardCharsets.UTF_8));
            Console.log("交换机: {} 消息发送成功!", X0_常量.普通交换机名称);
        }
        System.exit(0);
    }


    private static Channel 创建信道() throws IOException, TimeoutException {
        ConnectionFactory 连接工厂 = new ConnectionFactory();
        连接工厂.setHost(X0_常量.HOST);
        连接工厂.setPort(X0_常量.端口);
        连接工厂.setUsername(X0_常量.账号);
        连接工厂.setPassword(X0_常量.密码);

        Connection 连接 = 连接工厂.newConnection();
        Channel 信道 = 连接.createChannel();
        return 信道;
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        生产消息发送mq();
        System.exit(0);
    }
}

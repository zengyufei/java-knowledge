package M_12_Topic交换机模式;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class R1_生产者 {


    public static void 生产消息发送mq() throws IOException, TimeoutException {
        final Channel 信道 = 创建信道();

        /**
         * 声明交换机
         * 1.交换机的名称
         * 2.交换机的类型
         */
        信道.exchangeDeclare(R0_常量.交换机名称, BuiltinExchangeType.TOPIC);

        // 发送消息
        Map<String, String> routingKeyMap = new HashMap<>(16);
        routingKeyMap.put("quick.orange.rabbit", "Q1Q2接收到消息, 来自路由[quick.orange.rabbit]");
        routingKeyMap.put("lazy.orange.elephant", "Q1Q2接收到消息, 来自路由[lazy.orange.elephant]");
        routingKeyMap.put("quick.orange.fox", "Q1接收到消息, 来自路由[quick.orange.fox]");
        routingKeyMap.put("lazy.brown.fox", "Q2接收到消息, 来自路由[lazy.brown.fox]");
        routingKeyMap.put("lazy.pink.rabbit", "Q2接收到消息, 来自路由[lazy.pink.rabbit]");
        routingKeyMap.put("quick.brown.fox", "Q1Q2都未接收到消息, 来自路由[quick.brown.fox]");
        routingKeyMap.put("quick.orange.male.rabbit", "Q1Q2都未接收到消息, 来自路由[quick.orange.male.rabbit]");
        routingKeyMap.put("lazy.orange.male.rabbit", "Q2接收到消息, 来自路由[lazy.orange.male.rabbit]");

        routingKeyMap.forEach((路由键, 消息内容) -> {
            try {

                // 8.发送消息
                /**
                 * @param exchange 交换机名称
                 * @param routingKey 路由key
                 * @param props 其他属性
                 * @param body 消息体
                 */
                信道.basicPublish(R0_常量.交换机名称, 路由键, null, 消息内容.getBytes(StandardCharsets.UTF_8));
                Console.log("交换机: {} 路由键: {} 消息发送成功!", R0_常量.交换机名称, 路由键);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private static Channel 创建信道() throws IOException, TimeoutException {
        ConnectionFactory 连接工厂 = new ConnectionFactory();
        连接工厂.setHost(R0_常量.HOST);
        连接工厂.setPort(R0_常量.端口);
        连接工厂.setUsername(R0_常量.账号);
        连接工厂.setPassword(R0_常量.密码);

        Connection 连接 = 连接工厂.newConnection();
        Channel 信道 = 连接.createChannel();
        return 信道;
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        生产消息发送mq();
        System.exit(0);
    }
}

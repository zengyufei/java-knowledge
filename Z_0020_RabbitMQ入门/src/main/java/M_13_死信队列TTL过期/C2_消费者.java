package M_13_死信队列TTL过期;

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

public class C2_消费者 {

    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() throws IOException, TimeoutException {
        Channel 信道 = 创建信道();

        // prefetchSize 和global这两项，实际过程中几乎不使用
        // prefetchSize：可接收消息的大小的
        // prefetchCount：处理消息最大的数量
        // global：是不是针对整个Connection的，因为一个Connection可以有多个Channel，如果是false则说明只是针对于这个Channel的
        信道.basicQos(1);

        // 声明普通交换机、死信交换机
        信道.exchangeDeclare(C0_常量.普通交换机名称, BuiltinExchangeType.DIRECT);
        信道.exchangeDeclare(C0_常量.死信交换机名称, BuiltinExchangeType.DIRECT);

        // 声明普通队列、死信队列
        //声明一个队列,名称随机，当消费者断开与队列的连接时，队列自动删除
        // 声明普通队列、死信队列
        // 普通队列设置参数
        Map<String, Object> argMap = new HashMap<>(16);
        // 过期时间,不建议在消费者设置,建议在生产者指定,更灵活
        //  argMap.put("x-message-ttl", 10000);
        // 正常队列设置死信交换机
        argMap.put("x-dead-letter-exchange", C0_常量.死信交换机名称);
        // 设置死信RoutingKey
        argMap.put("x-dead-letter-routing-key", C0_常量.死信路由键);
        信道.queueDeclare(C0_常量.普通队列名称, false, false, false, argMap);
        信道.queueDeclare(C0_常量.死信队列名称, false, false, false, null);

        // 普通队列、死信队列绑定对应的交换机
        信道.queueBind(C0_常量.普通队列名称, C0_常量.普通交换机名称, C0_常量.普通路由键);
        信道.queueBind(C0_常量.死信队列名称, C0_常量.死信交换机名称, C0_常量.死信路由键);
        System.out.println("等待接收消息......");

        /**
         * 接收消息
         * @param queue             队列名称
         * @param autoAck           消费成功后是否自动应答 true-是 false-否
         * @param deliverCallback   接收消息后的回调
         * @param cancelCallback    取消消息后的回调
         */
        信道.basicConsume(C0_常量.普通队列名称, true,
                (consumerTag, message) -> {
                    Console.log("普通队列名称接收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8));
                },
                consumerTag -> Console.log("普通队列名称消息消费被中断,取消消费消息"));
        // 对消息进行阻断不在往下运行
//        System.in.read();
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

    public static void main(String[] args) throws IOException, TimeoutException {
        监听队列接收mq消息();
    }
}

package M_12_Topic交换机模式;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class R2_消费者 {

    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() throws IOException, TimeoutException {
        Channel 信道 = 创建信道();

        /**
         * 声明交换机
         * 1.交换机的名称
         * 2.交换机的类型
         */
        信道.exchangeDeclare(R0_常量.交换机名称, BuiltinExchangeType.TOPIC);

        //声明一个队列,名称随机，当消费者断开与队列的连接时，队列自动删除
        final String 临时队列名称1 = 信道.queueDeclare().getQueue();
        final String 临时队列名称2 = 信道.queueDeclare().getQueue();

        // 绑定交换机与队列
        信道.queueBind(临时队列名称1, R0_常量.交换机名称, R0_常量.路由键数组[0]);
        信道.queueBind(临时队列名称2, R0_常量.交换机名称, R0_常量.路由键数组[1]);
        Console.log("等待接受消息...");

        /**
         * 接收消息
         * @param queue             队列名称
         * @param autoAck           消费成功后是否自动应答 true-是 false-否
         * @param deliverCallback   接收消息后的回调
         * @param cancelCallback    取消消息后的回调
         */
        信道.basicConsume(临时队列名称1, true,
                (consumerTag, message) -> Console.log("\"*.*.rabbit\" 接收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8)),
                consumerTag -> Console.log("\"*.*.rabbit\" 消息消费被中断,取消消费消息"));
        信道.basicConsume(临时队列名称2, true,
                (consumerTag, message) -> Console.log("\"lazy.#\" 接收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8)),
                consumerTag -> Console.log("\"lazy.#\" 消息消费被中断,取消消费消息"));
        // 对消息进行阻断不在往下运行
        System.in.read();

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

    public static void main(String[] args) throws IOException, TimeoutException {
        监听队列接收mq消息();
    }
}

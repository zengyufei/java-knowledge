package M_16_测试自动重连;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class K1_生产者 {
    static final String simpleName = K1_生产者.class.getSimpleName();
    static K0_连接管理器 连接管理器 = new K0_连接管理器();

    public static void 生产消息发送mq(Channel 信道, String 消息内容) throws IOException, TimeoutException {

        // 7.声明队列
        /**
         * @param queue         队列名称
         * @param durable       是否持久化durable=false(默认) 所谓持久化消息是否存储到磁盘,如果false非持久化,true是持久化
         * @param exclusive     队列是否只提供一个消费者消费,是否进行消息共享,true-允许多个消费者消费 false-不允许
         * @param autoDelete    是否自动删除 最后一个消费者断开连接之后，该队列是否自动删除 true-自动删除 false-不自动删除
         * @param arguments     其他参数
         */
        信道.queueDeclare(K0_常量.队列名称, false, false, false, null);

        // 8.发送消息
        /**
         * @param exchange 交换机名称
         * @param routingKey 路由key,本次是队列名称
         * @param props 其他属性
         * @param body 消息体
         */
        信道.basicPublish("", K0_常量.队列名称, null, 消息内容.getBytes(StandardCharsets.UTF_8));
        Console.log("{}消息发送成功!消息: {}", simpleName, 消息内容);
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Channel 信道 = 连接管理器.创建信道();
        while (true) {
            final String 消息 = StrUtil.format("【{}】 序号：ttt ,时间：{}", IdUtil.fastSimpleUUID(), DateUtil.now());
            try {
                生产消息发送mq(信道, 消息);
            } catch (Exception e) {
                log.warn(ExceptionUtil.stacktraceToString(e));
                if (e instanceof AlreadyClosedException) {
                    信道 = 连接管理器.重连信道();
                }
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

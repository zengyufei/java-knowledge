package M_09_异步未确认消息处理;

import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

public class B1_生产者 {

    static int MESSAGE_COUNT = 10;
    static Channel 信道;

    static {
        信道 = B0_创建信道.创建信道();
    }

    public static void 生产消息发送mq() {
        Console.log("======================================================");
        Console.log(B1_生产者.class.getSimpleName() + " =" + 信道.hashCode());

        /**
         * 线程安全有序的哈希表,适用于高并发的情况下
         * 1.轻松的将序号和消息进行关联
         * 2.轻松批量删除消息 根据序号
         * 3.支持高并发（多线程）
         */
        ConcurrentSkipListMap<Long, String> skipListMap = new ConcurrentSkipListMap<>();

        try {
            // 开启发布确认
            信道.confirmSelect();


            // 7.声明队列
            /**
             * @param queue         队列名称
             * @param durable       是否持久化durable=false(默认) 所谓持久化消息是否存储到磁盘,如果false非持久化,true是持久化
             * @param exclusive     队列是否只提供一个消费者消费,是否进行消息共享,true-允许多个消费者消费 false-不允许
             * @param autoDelete    是否自动删除 最后一个消费者断开连接之后，该队列是否自动删除 true-自动删除 false-不自动删除
             * @param arguments     其他参数
             */
            信道.queueDeclare(B0_常量.队列名称, false, false, false, null);

            // 消息确认成功回调
            ConfirmCallback confirmCallback = (deliveryTag, multiple) -> {
                if (multiple) {
                    // 2.删除已经确认的消息,剩下就是未确认的消息
                    ConcurrentNavigableMap<Long, String> headMap = skipListMap.headMap(deliveryTag);
                    headMap.clear();
                }
                skipListMap.remove(deliveryTag);
                Console.log("确认的消息: " + deliveryTag);
            };

            // 消息确认失败回调
            ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
                // 3.获取未确认的消息
                String message = skipListMap.get(deliveryTag);
                Console.log("未确认的消息: " + deliveryTag + "  msg: " + message);
            };

            /**
             * 1.消息确认成功回调
             * 2.消息确认失败回调
             */
            信道.addConfirmListener(confirmCallback, nackCallback);// 异步通知

            // 8.发送消息
            /**
             * @param exchange 交换机名称
             * @param routingKey 路由key,本次是队列名称
             * @param props 其他属性
             * @param body 消息体
             */
            long start = System.currentTimeMillis();
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                final long nextPublishSeqNo = 信道.getNextPublishSeqNo();
                String message = i + "";
                信道.basicPublish("", B0_常量.队列名称, null, message.getBytes(StandardCharsets.UTF_8));
                // 1.记录下所有要发送的消息 消息的总和
                skipListMap.put(nextPublishSeqNo, message);
                // 必须休眠，否则消息来不及监听就已经结束了，
                TimeUnit.MILLISECONDS.sleep(100);
            }

            long end = System.currentTimeMillis();
            Console.log("发布" + MESSAGE_COUNT + "个异步发布消息耗时: " + (end - start) + "ms");
            Console.log(skipListMap.size());
            for (Map.Entry<Long, String> entry : skipListMap.entrySet()) {
                final Long key = entry.getKey();
                final String value = entry.getValue();
                Console.log("key:" + key + " value:" + value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        生产消息发送mq();
    }


}

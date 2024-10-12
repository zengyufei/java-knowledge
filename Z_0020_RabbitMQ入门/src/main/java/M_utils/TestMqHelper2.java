package M_utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public class TestMqHelper2 {

    private static AtomicBoolean countSecond(CountDownLatch countDownLatch, int maxSecond, Consumer<Integer> consumer) {
        AtomicBoolean isBreak = new AtomicBoolean(false);
        AtomicInteger count = new AtomicInteger(1);
        ThreadUtil.execute(() -> {
            while (!isBreak.get()) {
                final int andIncrement = count.getAndIncrement();
                if (andIncrement >= maxSecond) {
                    isBreak.set(true);
                    countDownLatch.countDown();
                }
                else {
                    try {
                        consumer.accept(andIncrement);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return isBreak;
    }

    @Test
    public void test_001_run() throws Exception {

        ThreadUtil.execute(() -> {
            try {
                System.out.println("运行 正常 消费");
                consumer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        System.in.read();
    }


    @Test
    public void consumer() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        MqHelper.initArgs("10.0.0.4", "root", "root", 5672);
        AtomicBoolean isBreak = countSecond(countDownLatch, 900, second -> {
            log.trace("消费: " + second + "秒");
        });
        ThreadUtil.execute(() -> {
            final MqConfig mqConfig = MqConfig.of()
                    .title("消费者")
                    .changeName("consumer")
                    .queueName("consumer_queue")
                    .deadConfig(DeadConfig.of().changeName("consumer_dead").queueName("consumer_dead_queue").build())
                    .build();
            final String now = DateUtil.now();
            log.trace("consumer while:  " + now);
            try {
                MqHelper.consumeMsg(mqConfig, (channel, s) -> {
                    if (isBreak.get()) {
                        System.exit(0);
                    }
                    log.info("消费者 {}号 收到消息:{}", channel.getChannelNumber(), (new String(s) + ",当前时间:" + now));
                    // 拒绝的方式丢入死信
                    return ConsumeAction.REJECT;
                });
            } catch (Exception e) {
                log.error(ExceptionUtil.stacktraceToString(e));
            }
        });
        countDownLatch.await();
        System.out.println("consumer()  完成任务!");
    }

    @Test
    public void consumerDead() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        MqHelper.initArgs("10.0.0.4", "root", "root", 5672);
        AtomicBoolean isBreak = countSecond(countDownLatch, 900, second -> {
            log.trace("死信: " + second + "秒");
        });
        ThreadUtil.execute(() -> {
            final MqConfig mqConfig = MqConfig.of()
                    .title("死信")
                    .changeName("consumer_dead")
                    .queueName("consumer_dead_queue")
                    .build();
            final String now = DateUtil.now();
            log.trace("consumerDead while:  " + now);
            try {
                MqHelper.consumeMsg(mqConfig, (channel, s) -> {
                    if (isBreak.get()) {
                        System.exit(0);
                    }
                    log.info("死信 {}号 收到消息:{}", channel.getChannelNumber(), (new String(s) + ",当前时间:" + now));
                    return ConsumeAction.ACCEPT;
                });
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                log.error(ExceptionUtil.stacktraceToString(e));
                // throw new RuntimeException(e);
            }
        });
        countDownLatch.await();
        System.out.println("consumerDead()  完成任务!");
    }

}

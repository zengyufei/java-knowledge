package M_02_工作队列模式;


import java.util.concurrent.TimeUnit;

public class P4_运行 {

    public static void main(String[] args) throws InterruptedException {
        String 消息 = "work " + 0;
        P1_生产者.生产消息发送mq(消息);

        P2_消费者.监听队列接收mq消息();
        P3_消费者.监听队列接收mq消息();

        for (int i = 0; i < 5; i++) {
            消息 = "work " + (i + 1);
            P1_生产者.生产消息发送mq(消息);

            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("结束");

        P1_生产者.关闭();
        P2_消费者.关闭();
        P3_消费者.关闭();

        System.out.println("全部关闭");

    }
}

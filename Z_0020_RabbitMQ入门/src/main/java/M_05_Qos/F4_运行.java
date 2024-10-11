package M_05_Qos;


import java.util.concurrent.TimeUnit;

public class F4_运行 {

    public static void main(String[] args) throws InterruptedException {
        String 消息 = "work " + 0;
        F1_生产者.生产消息发送mq(消息);

        F2_消费者.监听队列接收mq消息();
        F3_消费者.监听队列接收mq消息();

        for (int i = 0; i < 5; i++) {
            消息 = "work " + (i + 1);
            F1_生产者.生产消息发送mq(消息);
        }

        System.out.println("结束");
//
//        F1_生产者.关闭();
//        F2_消费者.关闭();
//        F3_消费者.关闭();
//
//        System.out.println("全部关闭");

    }
}

package M_01_Hello_World;

public class H3_运行 {

    public static void main(String[] args) {
        String 消息 = "hello world";

        H1_生产者.生产消息发送mq(消息);

        H2_消费者.监听队列接收mq消息();
    }
}

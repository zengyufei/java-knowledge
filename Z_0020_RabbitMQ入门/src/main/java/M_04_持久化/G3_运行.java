package M_04_持久化;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class G3_运行 {

    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {

        G1_生产者.生产消息发送mq("hello world");
        G1_生产者.生产消息发送mq("测试消息持久化");

        G2_消费者.监听队列接收mq消息();
    }
}

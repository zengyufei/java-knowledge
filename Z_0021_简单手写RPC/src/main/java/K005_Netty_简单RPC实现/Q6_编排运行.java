package K005_Netty_简单RPC实现;


import java.util.concurrent.TimeUnit;

public class Q6_编排运行 {
    public static void main(String[] args) throws InterruptedException {
        new Q6_服务端().start();

        TimeUnit.SECONDS.sleep(3);

        new Q6_客户端().start();

        TimeUnit.SECONDS.sleep(1);

        new Q6_客户端_副本().start();
    }
}

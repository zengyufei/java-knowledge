package K004_认识Netty;


import java.util.concurrent.TimeUnit;

public class N6_编排运行 {
    public static void main(String[] args) throws InterruptedException {
        new N4_服务端().start();

        TimeUnit.SECONDS.sleep(3);

        new N5_客户端().start();

        TimeUnit.SECONDS.sleep(1);

        new N5_客户端_副本().start();
    }
}

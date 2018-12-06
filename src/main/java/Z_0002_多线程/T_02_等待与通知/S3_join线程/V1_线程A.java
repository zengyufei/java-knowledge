package Z_0002_多线程.T_02_等待与通知.S3_join线程;

public class V1_线程A extends Thread {

    @Override
    public void run() {
        System.out.println("线程 A 运行...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package Z_0002_多线程.T_02_等待与通知.S3_join线程;

public class V1_线程C extends Thread {

    @Override
    public void run() {
        System.out.println("线程 C 运行...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package T_002_等待与通知.S3_join线程;

public class V1_线程B extends Thread {

    @Override
    public void run() {
        System.out.println("线程 B 运行...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

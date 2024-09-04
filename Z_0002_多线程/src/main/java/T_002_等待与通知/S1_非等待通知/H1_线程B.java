package T_002_等待与通知.S1_非等待通知;

public class H1_线程B extends Thread {

    @Override
    public void run() {
        try {
            while (true) {
                if (H1_线程A.list.size() == 5) {
                    System.out.println("==5了，线程B 要退出了！");
                    throw new InterruptedException();
                }
                System.out.println("我是轮训线程，==5了吗？");
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

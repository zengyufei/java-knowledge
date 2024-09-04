package T_002_等待与通知.S2_wait与notify;

public class R1_线程A extends Thread {

    public final static Object 锁 = new Object();

    @Override
    public void run() {
        try {
            synchronized (R1_线程A.锁) {
                System.out.println("等待开始..1s");
                // 必须使用 synchronized 持有 R1_线程A.锁，否则会报错，因为线程试图等待一个自己并不拥有的对象（锁）的监控器或者通知其他线程等待该对象（锁）的监控器时，抛出该异常。
                R1_线程A.锁.wait();
                System.out.println("A 等到 B，A 可以结束");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

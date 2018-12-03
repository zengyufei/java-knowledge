package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;

public class S3_复现多线程同步懒汉式单例 {

    private static S3_复现多线程同步懒汉式单例 同步懒汉式单例;

    /**
     * 使用同步方法 synchronized, 能够在多线程中很好的工作，遗憾的是，效率很低
     */
    public static synchronized S3_复现多线程同步懒汉式单例 getInstance() {
        if (同步懒汉式单例 == null) {
            try {
                Thread.sleep(10000);
                同步懒汉式单例 = new S3_复现多线程同步懒汉式单例();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 同步懒汉式单例;
    }
}

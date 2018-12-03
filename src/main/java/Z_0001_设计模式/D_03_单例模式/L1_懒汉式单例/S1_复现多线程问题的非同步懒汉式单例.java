package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;

/**
 * 不使用同步方法，在不同情况下是可以实现单例的。但是在高并发的情况下可能会产生多个实例
 */
public class S1_复现多线程问题的非同步懒汉式单例 {

    private static S1_复现多线程问题的非同步懒汉式单例 非同步懒汉式单例;

    public static S1_复现多线程问题的非同步懒汉式单例 getInstance() {
        if (非同步懒汉式单例 == null) {
            try {
                Thread.sleep(3000);
                非同步懒汉式单例 = new S1_复现多线程问题的非同步懒汉式单例();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 非同步懒汉式单例;
    }
}

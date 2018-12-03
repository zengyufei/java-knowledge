package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;

public class S5_复现多线程双重检验加锁懒汉式单例 {

    /**
     * volatile保证在多线程的时候，每个线程能正确的处理这个单例
     */
    private static volatile S5_复现多线程双重检验加锁懒汉式单例 双重检验加锁懒汉式单例;

    /**
     * 使用同步方法 synchronized, 能够在多线程中很好的工作，遗憾的是，效率很低
     * 使用双重检验加锁，在 getInstance 方法中减少同步
     */
    public static S5_复现多线程双重检验加锁懒汉式单例 getInstance() {
        if (双重检验加锁懒汉式单例 == null) {
            synchronized (S5_复现多线程双重检验加锁懒汉式单例.class) {
                try {
                    // Thread.sleep(3000); 结果一样，但是一条一条的出现，是因为 before
                    if (双重检验加锁懒汉式单例 == null) {
                        Thread.sleep(10000); // 10 条同时出现
                        双重检验加锁懒汉式单例 = new S5_复现多线程双重检验加锁懒汉式单例();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return 双重检验加锁懒汉式单例;
    }
}

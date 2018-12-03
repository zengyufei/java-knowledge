package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;

public class S5_复现多线程双重检验加锁懒汉式单例 {

    /**
     * volatile保证在多线程的时候，每个线程能正确的处理这个单例
     */
    private static volatile S5_复现多线程双重检验加锁懒汉式单例 双重检验加锁懒汉式单例;

    /**
     * 使用双重检验加锁，在 getInstance 方法中减少同步
     * 单例对象 volatile + 双重检测机制 -> 禁止指令重排序
     */
    public static S5_复现多线程双重检验加锁懒汉式单例 getInstance() {
        if (双重检验加锁懒汉式单例 == null) {
            synchronized (S5_复现多线程双重检验加锁懒汉式单例.class) {
                try {
                    // Thread.sleep(3000); 结果一样，但是一条一条的出现，是因为 before
                    if (双重检验加锁懒汉式单例 == null) {
                        Thread.sleep(10000); // 10 条同时出现
                        // 涉及到 JVM 流程，这里会有指令重排序
                        // 1. memory = allocate() 分配对象的内存空间
                        // 2. ctorInstance() 初始化对象
                        // 3. instance = memory 设置 instance 指向刚分配的对象
                        // 正常情况下单线程，指令重排不会影响最终对象
                        // 多线程情况下，当t1 还是执行分配内存空间的指令是，t2 线程已经完成初始化了，就会有问题
                        // 所以一个对象的 new，不是原子性的，会出现指令重排序的问题。
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

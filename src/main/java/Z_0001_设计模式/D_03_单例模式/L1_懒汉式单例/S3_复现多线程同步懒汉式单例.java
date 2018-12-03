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
                // 涉及到 JVM 流程，这里会有指令重排序
                // 1. memory = allocate() 分配对象的内存空间
                // 2. ctorInstance() 初始化对象
                // 3. instance = memory 设置 instance 指向刚分配的对象
                // 正常情况下单线程，指令重排不会影响最终对象
                // 多线程情况下，当t1 还是执行分配内存空间的指令是，t2 线程已经完成初始化了，就会有问题
                // 所以一个对象的 new，不是原子性的，会出现重排序的问题。
                同步懒汉式单例 = new S3_复现多线程同步懒汉式单例();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 同步懒汉式单例;
    }
}

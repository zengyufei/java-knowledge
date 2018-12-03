package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;

/**
 * 线程安全；延迟加载；效率较高。
 */
public class S5_双重检验加锁懒汉式单例 {

    private static S5_双重检验加锁懒汉式单例 双重检验加锁懒汉式单例;

    /**
     * 使用双重检验加锁，在 getInstance 方法中减少同步
     */
    public static S5_双重检验加锁懒汉式单例 getInstance() {
        synchronized (S5_双重检验加锁懒汉式单例.class) {
            if (双重检验加锁懒汉式单例 == null) {
                双重检验加锁懒汉式单例 = new S5_双重检验加锁懒汉式单例();
            }
        }
        return 双重检验加锁懒汉式单例;
    }
}

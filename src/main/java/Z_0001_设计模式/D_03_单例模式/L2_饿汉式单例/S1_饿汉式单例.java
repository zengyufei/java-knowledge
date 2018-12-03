package Z_0001_设计模式.D_03_单例模式.L2_饿汉式单例;

/**
 * 饿汉就是类一旦加载，就把单例初始化完成，保证 getInstance 的时候，单例是已经存在的了
 */
public class S1_饿汉式单例 {
    private S1_饿汉式单例() {
    }

    /**
     * 饿汉式单例天生就是线程安全的
     */
    private static S1_饿汉式单例 饿汉式单例 = new S1_饿汉式单例();

    /* 原理与上面相同
    static {
        饿汉式单例 = new S1_饿汉式单例();
    }
    */

    public static S1_饿汉式单例 getInstance() {
        return 饿汉式单例;
    }
}

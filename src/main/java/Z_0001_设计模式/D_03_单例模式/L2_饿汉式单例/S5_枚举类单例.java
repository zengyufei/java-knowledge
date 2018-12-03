package Z_0001_设计模式.D_03_单例模式.L2_饿汉式单例;

import Z_utils.Console;

/**
 * 不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
 */
public enum  S5_枚举类单例 {
    INSTANCE;

    public void doSomeThing() {
        Console.getThisMethodFullName();
    }
}

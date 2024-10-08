package D_03_单例模式.L2_饿汉式单例;


import Z_utils.输出;

/**
 * 优点：系统内存中该类只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使用单例模式可以提高系统性能。
 * 缺点：当想实例化一个单例类的时候，必须要记住使用相应的获取对象的方法，而不是使用 new，可能会给其他开发人员造成困扰，特别是看不到源码的时候。
 */
public class P6_运行 {

    public static void 正常测试() {
        输出.当前方法全名("开始。");
        P5_枚举类单例 s1 = P5_枚举类单例.INSTANCE;
        P5_枚举类单例 s2 = P5_枚举类单例.INSTANCE;
        System.out.println((s1 == s2) ? "正常测试通过!" : "正常测试失败！");
        s1.doSomeThing();
    }

    public static void main(String[] args) {
        正常测试(); // 天生线程安全，不需要测试多线程，缺点，牺牲空间换线程安全
    }

}

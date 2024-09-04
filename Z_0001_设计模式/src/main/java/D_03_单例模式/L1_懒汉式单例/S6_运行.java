package D_03_单例模式.L1_懒汉式单例;


import Z_utils.多线程并发测试;
import Z_utils.输出;

public class S6_运行 {

    private static int currentHashCode = 0;

    public static void 正常测试() {
        输出.当前方法全名("开始。");
        S5_双重检验加锁懒汉式单例 s1 = S5_双重检验加锁懒汉式单例.getInstance();
        S5_双重检验加锁懒汉式单例 s2 = S5_双重检验加锁懒汉式单例.getInstance();
        S5_双重检验加锁懒汉式单例 s3 = new S5_双重检验加锁懒汉式单例();
        System.out.println((s1 == s2 && s2 != s3) ? "正常测试通过!" : "正常测试失败！");
    }


    public static void 多线程测试() {
        输出.当前方法全名("开始。");

        多线程并发测试.多线程测试(() -> {
            int hashCode = S5_双重检验加锁懒汉式单例.getInstance().hashCode();
            if (currentHashCode == 0) {
                currentHashCode = hashCode;
            } else if (currentHashCode != hashCode) {
                System.out.println("出现线程不安全：原值 -> " + currentHashCode + "。新值 -> " + hashCode);
            }
        });

    }

    public static void main(String[] args) {
        正常测试();
        多线程测试();
    }

}

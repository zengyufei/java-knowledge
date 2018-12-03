package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;


import Z_utils.Console;

public class S2_测试非同步懒汉式单例 {

    private static void 正常测试() {
        Console.getThisMethodFullName("开始。");
        S1_非同步懒汉式单例 s1 = S1_非同步懒汉式单例.getInstance();
        S1_非同步懒汉式单例 s2 = S1_非同步懒汉式单例.getInstance();
        S1_非同步懒汉式单例 s3 = new S1_非同步懒汉式单例();
        System.out.println((s1 == s2 && s2 != s3) ? "正常测试通过!" : "正常测试失败！");
    }

    public static void main(String[] args) {
        正常测试();
    }

}

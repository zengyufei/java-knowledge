package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;


import Z_utils.Console;

import java.util.HashSet;
import java.util.Set;

public class S4_测试同步懒汉式单例 {

    private static void 正常测试() {
        Console.getThisMethodFullName("开始。");
        S3_同步懒汉式单例 s1 = S3_同步懒汉式单例.getInstance();
        S3_同步懒汉式单例 s2 = S3_同步懒汉式单例.getInstance();
        S3_同步懒汉式单例 s3 = new S3_同步懒汉式单例();
        System.out.println((s1 == s2 && s2 != s3) ? "正常测试通过!" : "正常测试失败！");
    }


    private static void 多线程测试() {
        Set<Integer> hashCodes = new HashSet<>();
        Console.getThisMethodFullName("开始。");
        Runnable task = () -> {
            int hashCode = S3_复现多线程同步懒汉式单例.getInstance().hashCode();
            hashCodes.add(hashCode);
        };
        // 10 万次测试，休眠 10 秒，10 秒后全部线程一起抢占 new 实体。
        for (int i = 0; i < 100000; i++) {
            new Thread(task, "" + i).start();
        }
        if (hashCodes.size() > 1) {
            for (Integer hashCode : hashCodes) {
                System.out.println(hashCode);
                System.out.println();
            }
        }
        System.out.println(hashCodes.size() == 1);
        System.out.println(hashCodes.size());
    }

    public static void main(String[] args) {
        正常测试();
        多线程测试();
    }

}

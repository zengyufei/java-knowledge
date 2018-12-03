package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;


import Z_utils.Console;

public class S2_测试复现多线程问题的非同步懒汉式单例 {

    private static void 多线程测试() {
        Console.getThisMethodFullName("开始。");
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程 " + threadName + " => " + S1_复现多线程问题的非同步懒汉式单例.getInstance().hashCode());
        };
        for (int i = 0; i < 10; i++) {
            new Thread(task, "" + i).start();
        }
    }

    public static void main(String[] args) {
        多线程测试();
    }

}

package Z_0001_设计模式.D_03_单例模式.L1_懒汉式单例;


import Z_utils.Console;

import java.util.concurrent.CountDownLatch;

public class S2_多线程测试复现同步问题 {

    private static int currentValue = 0;

    private static void 多线程测试() {
        Console.getThisMethodFullName("开始。");

        // 新建一个 CountDwonLatch 对象并传入计数器的值
        CountDownLatch cd = new CountDownLatch(10);
        Runnable task = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 涉及到 JVM 流程，这里会有指令重排序
            // 1. memory = allocate() 分配对象的内存空间
            // 2. ctorInstance() 初始化对象
            // 3. instance = memory 设置 instance 指向刚分配的对象
            // 正常情况下单线程，指令重排不会影响最终对象
            // 多线程情况下，当t1 还是执行分配内存空间的指令是，t2 线程已经完成初始化了，就会有问题
            // 所以一个对象的 new，不是原子性的，会出现重排序的问题。
            int hashCode = S1_非同步懒汉式单例.getInstance().hashCode();
            if (currentValue == 0) {
                currentValue = hashCode;
            } else if (currentValue != hashCode) {
                System.out.println("出现线程不安全：原值 -> " + currentValue + "。新值 -> " + hashCode);
            }
            cd.countDown();
        };

        // 1 万次测试，休眠 3 秒，3 秒后全部线程一起抢占 new 实体。
        for (int i = 0; i < 10000; i++) {
            new Thread(task, "" + i).start();
        }

        // 等待所有线程执行完成
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试完成！");
    }

    public static void main(String[] args) {
        多线程测试();
    }

}

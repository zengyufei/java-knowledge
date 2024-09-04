package T_002_等待与通知.S3_join线程;

/**
 * 现在有 A、B、C 三个线程，你怎样保证 B 在 A 执行完后执行，C 在 A 执行完后执行
 */
public class V2_测试 {

    public static void main(String[] args) throws InterruptedException {
        Thread 线程A = new V1_线程A();
        Thread 线程B = new V1_线程B();
        Thread 线程C = new V1_线程C();
        线程A.start();
        线程A.join();
        System.out.println("线程 A 运行结束。。。");
        线程B.start();
        线程B.join();
        System.out.println("线程 B 运行结束。。。");
        线程C.start();
        线程C.join();
        System.out.println("线程 C 运行结束。。。");
        System.out.println("主线程 运行结束。。。");
    }
}

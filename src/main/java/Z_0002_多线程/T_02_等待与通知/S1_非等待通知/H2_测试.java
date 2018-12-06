package Z_0002_多线程.T_02_等待与通知.S1_非等待通知;

public class H2_测试 {

    public static void main(String[] args) {
        H1_线程A 线程A = new H1_线程A();
        H1_线程B 线程B = new H1_线程B();
        线程A.start();
        线程B.start();
    }

}

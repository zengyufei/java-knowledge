package Z_0002_多线程.T_02_等待与通知.S2_wait与notify;

public class R2_测试 {

    public static void main(String[] args) {
        R1_线程A 线程A = new R1_线程A();
        R1_线程B 线程B = new R1_线程B();
        线程A.start();
        线程B.start();
    }

}

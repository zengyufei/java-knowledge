package Z_0001_设计模式.D_02_抽象工厂模式.S2_抽象工厂模式;


import Z_utils.Console;

public class I2_番茄酱美国披萨 extends I1_美国披萨 {

    @Override
    public void 加酱() {
        Console.getThisMethodName("：番茄酱");
    }
}

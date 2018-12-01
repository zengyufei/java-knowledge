package Z_0001_设计模式.D_02_抽象工厂模式.S2_抽象工厂模式;

import Z_utils.Console;

public class I4_番茄酱披萨工厂 extends I3_工厂接口 {

    @Override
    public I1_中国披萨 制作中国披萨() {
        System.out.println(Console.getThisMethodName());
        I1_中国披萨 中国披萨 = new I2_番茄酱中国披萨();
        中国披萨.加水();
        中国披萨.加面粉();
        中国披萨.加酱();
        return 中国披萨;
    }

    @Override
    public I1_美国披萨 制作美国披萨() {
        System.out.println(Console.getThisMethodName());
        I1_美国披萨 美国披萨 = new I2_番茄酱美国披萨();
        美国披萨.加水();
        美国披萨.加面粉();
        美国披萨.加酱();
        return 美国披萨;
    }
}

package Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式;

import Z_utils.Console;

public class I2_中国披萨 implements I1_披萨接口 {

    public void 加水() {
        System.out.println(Console.getThisMethodName(" 200 毫升。"));
    }

    public void 加面粉() {
        System.out.println(Console.getThisMethodName(" 400 克。"));
    }
}

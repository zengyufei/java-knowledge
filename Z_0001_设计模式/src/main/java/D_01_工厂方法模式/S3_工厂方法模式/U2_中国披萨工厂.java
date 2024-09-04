package D_01_工厂方法模式.S3_工厂方法模式;

import D_01_工厂方法模式.S2_简单工厂模式.J1_披萨接口;
import D_01_工厂方法模式.S2_简单工厂模式.J2_中国披萨;

public class U2_中国披萨工厂 implements D_01_工厂方法模式.S3_工厂方法模式.U1_工厂接口 {
    private J2_中国披萨 中国披萨;

    public J1_披萨接口 制作披萨() {
        System.out.println("中国披萨工厂开始生产中国披萨");
        中国披萨 = new J2_中国披萨();
        中国披萨.加水();
        中国披萨.加面粉();
        return 中国披萨;
    }

}

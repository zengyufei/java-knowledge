package Z_0001_设计模式.D_01_工厂方法模式.S3_工厂方法模式;

import Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式.I1_披萨接口;
import Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式.I2_中国披萨;

public class U2_中国披萨工厂 implements U1_工厂接口 {
    private I2_中国披萨 中国披萨;

    public I1_披萨接口 制作披萨() {
        System.out.println("中国披萨工厂开始生产中国披萨");
        中国披萨 = new I2_中国披萨();
        中国披萨.加水();
        中国披萨.加面粉();
        return 中国披萨;
    }

}

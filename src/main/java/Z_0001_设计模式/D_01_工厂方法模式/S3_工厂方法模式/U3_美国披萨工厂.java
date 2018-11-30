package Z_0001_设计模式.D_01_工厂方法模式.S3_工厂方法模式;

import Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式.I1_披萨接口;
import Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式.I3_美国披萨;

public class U3_美国披萨工厂 implements U1_工厂接口 {
    private I3_美国披萨 美国披萨;

    public I1_披萨接口 制作披萨() {
        System.out.println("美国披萨工厂开始生产美国披萨");
        美国披萨 = new I3_美国披萨();
        美国披萨.加水();
        美国披萨.加面粉();
        return 美国披萨;
    }

}

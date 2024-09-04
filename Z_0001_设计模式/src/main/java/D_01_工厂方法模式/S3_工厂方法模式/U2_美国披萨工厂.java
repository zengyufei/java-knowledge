package D_01_工厂方法模式.S3_工厂方法模式;

import D_01_工厂方法模式.S2_简单工厂模式.J1_披萨接口;
import D_01_工厂方法模式.S2_简单工厂模式.J2_美国披萨;

public class U2_美国披萨工厂 implements U1_工厂接口 {
    private J2_美国披萨 美国披萨;

    public J1_披萨接口 制作披萨() {
        System.out.println("美国披萨工厂开始生产美国披萨");
        美国披萨 = new J2_美国披萨();
        美国披萨.加水();
        美国披萨.加面粉();
        return 美国披萨;
    }

}

package Z_0001_设计模式.D_02_抽象工厂模式.S3_静态抽象工厂;

import Z_0001_设计模式.D_02_抽象工厂模式.S2_抽象工厂模式.I1_中国披萨;
import Z_0001_设计模式.D_02_抽象工厂模式.S2_抽象工厂模式.I1_美国披萨;
import Z_utils.输出;

public class J1_字符串披萨工厂 {

    public static I1_中国披萨 创建中国披萨(String 什么披萨) {
        输出.当前方法名(什么披萨);
        I1_中国披萨 中国披萨 = null;
        try {
            中国披萨 = (I1_中国披萨) Class.forName(什么披萨).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        中国披萨.加水();
        中国披萨.加面粉();
        中国披萨.加酱();
        return 中国披萨;
    }

    public static I1_美国披萨 创建美国披萨(String 什么披萨)  {
        输出.当前方法名(什么披萨);
        I1_美国披萨 美国披萨 = null;
        try {
            美国披萨 = (I1_美国披萨) Class.forName(什么披萨).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        美国披萨.加水();
        美国披萨.加面粉();
        美国披萨.加酱();
        return 美国披萨;
    }

}

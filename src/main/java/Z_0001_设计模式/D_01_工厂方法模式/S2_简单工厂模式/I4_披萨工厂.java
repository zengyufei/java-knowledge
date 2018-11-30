package Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式;

public class I4_披萨工厂 {

    private static I1_披萨接口 披萨;

    public static I1_披萨接口 制作披萨(String 披萨类型) {
        if ("中国".equals(披萨类型)) {
            System.out.println("披萨工厂开始生产 中国披萨。");
            披萨 = new I2_中国披萨();
            披萨.加水();
            披萨.加面粉();
        } else if ("美国".equals(披萨类型)) {
            System.out.println("披萨工厂开始生产 美国披萨。");
            披萨 = new I3_美国披萨();
            披萨.加水();
            披萨.加面粉();
        } else {
            System.out.println("抱歉，工厂无法生产此类 披萨！");
        }
        return 披萨;
    }
}

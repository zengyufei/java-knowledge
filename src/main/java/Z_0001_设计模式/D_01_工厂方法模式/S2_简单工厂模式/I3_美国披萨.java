package Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式;

public class I3_美国披萨 implements I1_披萨接口 {

    public void 加水() {
        System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 100 毫升。");
    }

    public void 加面粉() {
        System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 300 克。");
    }
}

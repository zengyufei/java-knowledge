package Z_0001_设计模式.D_01_工厂方法模式.S1_需求场景;

public class X2_制作披萨 {

    public static void 制作(){
        X1_披萨实体类 pizza = new X1_披萨实体类();
        pizza.加水();
        pizza.加面粉();
    }

    public static void main(String[] args) {
        制作();
    }
}

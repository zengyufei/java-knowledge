package Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式;

public class I5_制作披萨 {

    public static void 制作(){
        I2_中国披萨 中国披萨 = (I2_中国披萨) I4_披萨工厂.制作披萨("中国");
        I3_美国披萨 美国披萨 = (I3_美国披萨) I4_披萨工厂.制作披萨("美国");
        I3_美国披萨 其他披萨 = (I3_美国披萨) I4_披萨工厂.制作披萨("");
    }

    public static void main(String[] args) {
        制作();
    }
}

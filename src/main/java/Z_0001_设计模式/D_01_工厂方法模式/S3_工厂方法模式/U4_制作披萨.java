package Z_0001_设计模式.D_01_工厂方法模式.S3_工厂方法模式;

public class U4_制作披萨 {

    public static void 制作(){
        U1_工厂接口 中国披萨工厂 = new U2_中国披萨工厂();
        中国披萨工厂.制作披萨();

        U1_工厂接口 美国披萨工厂 = new U3_美国披萨工厂();
        美国披萨工厂.制作披萨();
    }

    public static void main(String[] args) {
        制作();
    }
}

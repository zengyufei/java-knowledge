package D_05_原型模式.S1_需求场景;

public class X1_手机 {

    public String 颜色;
    public String 价格;
    public X1_账号 账号;

    @Override
    public String toString() {
        return "X1_手机{" +
                "颜色='" + 颜色 + '\'' +
                ", 价格='" + 价格 + '\'' +
                ", 账号=" + 账号 +
                '}';
    }
}

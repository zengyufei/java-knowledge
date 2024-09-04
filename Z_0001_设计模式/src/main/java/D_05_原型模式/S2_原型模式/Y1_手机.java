package D_05_原型模式.S2_原型模式;

import D_05_原型模式.S1_需求场景.X1_账号;

public class Y1_手机 implements Cloneable {

    public String 颜色;
    public String 价格;
    public X1_账号 账号;

    @Override
    public String toString() {
        return "Y1_手机{" +
                "颜色='" + 颜色 + '\'' +
                ", 价格='" + 价格 + '\'' +
                ", 账号=" + 账号 +
                '}';
    }

    @Override
    protected Object clone()  {
        Y1_手机 重新激活手机 = null;
        try {
            重新激活手机 = (Y1_手机) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return 重新激活手机;
    }
}

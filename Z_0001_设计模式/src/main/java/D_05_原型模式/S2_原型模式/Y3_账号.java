package D_05_原型模式.S2_原型模式;

import java.io.Serializable;

public class Y3_账号 implements Serializable {

    private static final long serialVersionUID = -8251455193402980687L;

    public String 用户名;
    public String 密码;

    @Override
    public String toString() {
        return "X1_账号{" +
                "用户名='" + 用户名 + '\'' +
                ", 密码='" + 密码 + '\'' +
                '}';
    }
}

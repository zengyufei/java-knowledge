package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包;

/**
 * 描述：
 * @author zengyufei
 */
public class C3_登录请求数据包 extends C1_抽象数据包 {

    private String 账号;
    private String 密码;
    private String 姓名;

    @Override
    public byte 指令() {
        return C1_指令.LOGIN_REQUEST;
    }

    public String get账号() {
        return 账号;
    }

    public void set账号(String 账号) {
        this.账号 = 账号;
    }

    public String get密码() {
        return 密码;
    }

    public void set密码(String 密码) {
        this.密码 = 密码;
    }

    public String get姓名() {
        return 姓名;
    }

    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }
}

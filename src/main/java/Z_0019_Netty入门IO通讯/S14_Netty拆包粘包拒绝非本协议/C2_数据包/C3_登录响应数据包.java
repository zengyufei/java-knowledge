package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包;

/**
 * 描述：
 * @author zengyufei
 */
public class C3_登录响应数据包 extends C1_抽象数据包 {

    private String 代码;
    private String 是否成功;

    @Override
    public byte 指令() {
        return C1_指令.LOGIN_RESPONSE;
    }

    public String get代码() {
        return 代码;
    }

    public void set代码(String 代码) {
        this.代码 = 代码;
    }

    public String get是否成功() {
        return 是否成功;
    }

    public void set是否成功(String 是否成功) {
        this.是否成功 = 是否成功;
    }
}

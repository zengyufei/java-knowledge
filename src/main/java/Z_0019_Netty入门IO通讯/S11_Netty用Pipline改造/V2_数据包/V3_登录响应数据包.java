package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包;

/**
 * 描述：
 * @author zengyufei
 */
public class V3_登录响应数据包 extends V1_抽象数据包 {

    private String 代码;
    private String 是否成功;

    @Override
    public byte 指令() {
        return V1_指令.LOGIN_RESPONSE;
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

package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

/**
 * 描述：
 * @author zengyufei
 */
public class F4_登录响应数据包 extends F1_抽象数据包 {

    String 状态;
    String 是否成功;

    @Override
    Byte 指令() {
        return F1_指令.登录响应;
    }

    // json 序列化需要 getter/setter

    public String get状态() {
        return 状态;
    }

    public void set状态(String 状态) {
        this.状态 = 状态;
    }

    public String get是否成功() {
        return 是否成功;
    }

    public void set是否成功(String 是否成功) {
        this.是否成功 = 是否成功;
    }
}

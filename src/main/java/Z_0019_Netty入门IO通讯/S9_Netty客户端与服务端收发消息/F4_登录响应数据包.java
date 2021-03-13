package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F1_抽象数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F1_指令;

/**
 * 描述：
 * @author zengyufei
 */
public class F4_登录响应数据包 extends F1_抽象数据包 {

    String 代码;
    String 是否成功;

    @Override
    public Byte 指令() {
        return F1_指令.登录响应;
    }

    // json 序列化需要 getter/setter

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

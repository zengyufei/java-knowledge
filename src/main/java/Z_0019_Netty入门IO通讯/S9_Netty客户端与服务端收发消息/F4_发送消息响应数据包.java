package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F1_抽象数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F1_指令;

/**
 * 描述：
 * @author zengyufei
 */
public class F4_发送消息响应数据包 extends F1_抽象数据包 {

    String 消息;

    @Override
    public Byte 指令() {
        return F1_指令.发送消息响应;
    }

    // json 序列化需要 getter/setter

    public String get消息() {
        return 消息;
    }

    public void set消息(String 消息) {
        this.消息 = 消息;
    }
}

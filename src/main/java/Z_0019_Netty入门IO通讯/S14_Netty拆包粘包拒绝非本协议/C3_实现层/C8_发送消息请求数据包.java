package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_实现层;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C1_常量.C1_指令;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_接口层.C3_抽象数据包;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class C8_发送消息请求数据包 extends C3_抽象数据包 {

    private String 消息;

    @Override
    public byte 指令() {
        return C1_指令.SEND_MESSAGE_REQUEST;
    }

    public String get消息() {
        return 消息;
    }

    public void set消息(String 消息) {
        this.消息 = 消息;
    }
}

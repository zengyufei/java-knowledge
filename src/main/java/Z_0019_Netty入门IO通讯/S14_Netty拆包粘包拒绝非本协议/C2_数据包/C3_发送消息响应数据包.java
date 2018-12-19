package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包;

/**
 * 描述：
 * @author zengyufei
 */
public class C3_发送消息响应数据包 extends C1_抽象数据包 {

    private String 消息;

    @Override
    public byte 指令() {
        return C1_指令.SEND_MESSAGE_RESPONSE;
    }

    public String get消息() {
        return 消息;
    }

    public void set消息(String 消息) {
        this.消息 = 消息;
    }
}

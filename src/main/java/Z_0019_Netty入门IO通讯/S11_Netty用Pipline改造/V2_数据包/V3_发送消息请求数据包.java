package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包;

/**
 * 描述：
 * @author zengyufei
 */
public class V3_发送消息请求数据包 extends V1_抽象数据包 {

    private String 消息;

    @Override
    public byte 指令() {
        return V1_指令.SEND_MESSAGE_REQUEST;
    }

    public String get消息() {
        return 消息;
    }

    public void set消息(String 消息) {
        this.消息 = 消息;
    }
}

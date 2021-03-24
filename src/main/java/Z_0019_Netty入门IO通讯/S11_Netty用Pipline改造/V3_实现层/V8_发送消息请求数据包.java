package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V1_常量.V1_指令类型;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_接口层.V3_抽象数据包;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V8_发送消息请求数据包 extends V3_抽象数据包 {

    private String 消息;

    @Override
    public byte 指令() {
        return V1_指令类型.SEND_MESSAGE_REQUEST;
    }

    public String get消息() {
        return 消息;
    }

    public void set消息(String 消息) {
        this.消息 = 消息;
    }
}

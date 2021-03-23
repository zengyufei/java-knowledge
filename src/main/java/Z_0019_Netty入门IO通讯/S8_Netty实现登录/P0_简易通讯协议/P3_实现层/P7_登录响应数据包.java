package Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P3_实现层;

import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P1_常量.P1_指令类型;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P2_接口层.P3_抽象数据包;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class P7_登录响应数据包 extends P3_抽象数据包 {

    private String 消息;
    private String 是否成功;

    @Override
    public Byte 指令() {
        return P1_指令类型.登录响应指令;
    }
}

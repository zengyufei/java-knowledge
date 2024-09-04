package S8_Netty实现登录.P0_简易通讯协议.P3_实现层;

import S8_Netty实现登录.P0_简易通讯协议.P1_常量.P1_指令类型;
import S8_Netty实现登录.P0_简易通讯协议.P2_接口层.P3_抽象数据包;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class P6_登录请求数据包 extends P3_抽象数据包 {

    private String 用户名;
    private String 密码;
    private String 姓名;

    @Override
    public Byte 指令() {
        return P1_指令类型.登录请求指令;
    }
}

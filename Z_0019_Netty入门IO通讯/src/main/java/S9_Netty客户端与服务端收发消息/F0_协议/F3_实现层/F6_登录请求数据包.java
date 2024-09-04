package S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层;

import S9_Netty客户端与服务端收发消息.F0_协议.F1_常量.F1_指令类型;
import S9_Netty客户端与服务端收发消息.F0_协议.F2_接口层.F3_抽象数据包;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述：
 *
 * @author zengyufei
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class F6_登录请求数据包 extends F3_抽象数据包 {

    private String 账号;
    private String 密码;
    private String 姓名;

    @Override
    public Byte 指令() {
        return F1_指令类型.登录请求;
    }
}

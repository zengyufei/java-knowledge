package S7_Netty简易通讯协议.B3_实现层;

import S7_Netty简易通讯协议.B1_常量.B1_指令类型;
import S7_Netty简易通讯协议.B2_接口层.B3_抽象数据包;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述：
 *
 * @author zengyufei
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class B5_具体数据包 extends B3_抽象数据包 {

    //super Byte version;

    private String 用户唯一标识;
    private String 用户名;
    private String 密码;

    @Override
    public Byte 获取指令() {
        return B1_指令类型.登录请求;
    }

}

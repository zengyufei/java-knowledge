package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F1_常量;

/**
 * 描述：
 *
 * @author zengyufei
 */
public interface F1_指令类型 {

    Byte 登录请求 = 1;
    Byte 登录响应 = 2;

    Byte 发送消息请求 = 3;
    Byte 发送消息响应 = 4;

}

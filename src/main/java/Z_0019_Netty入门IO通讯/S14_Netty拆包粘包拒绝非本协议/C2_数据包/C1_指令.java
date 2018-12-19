package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包;

/**
 * 描述：
 * @author zengyufei
 */
public interface C1_指令 {

    /**
     * 登录请求
     */
    byte LOGIN_REQUEST = 1;
    /**
     * 登录响应
     */
    byte LOGIN_RESPONSE = 2;

    /**
     * 发送消息请求
     */
    byte SEND_MESSAGE_REQUEST = 3;
    /**
     * 发送消息响应
     */
    byte SEND_MESSAGE_RESPONSE = 4;

}

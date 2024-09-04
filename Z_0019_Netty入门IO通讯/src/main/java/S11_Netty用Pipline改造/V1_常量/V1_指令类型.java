package S11_Netty用Pipline改造.V1_常量;

/**
 * 描述：
 *
 * @author zengyufei
 */
public interface V1_指令类型 {

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

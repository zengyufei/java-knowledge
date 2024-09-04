package S17_Netty实现一对一单聊.Y1_常量;

public interface Y1_指令类型 {

    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
    byte SEND_MESSAGE_REQUEST = 3;
    byte SEND_MESSAGE_RESPONSE = 4;

}

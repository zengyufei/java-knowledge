package S17_Netty实现一对一单聊.Y4_应用层;

import S17_Netty实现一对一单聊.Y1_常量.Y1_指令类型;
import S17_Netty实现一对一单聊.Y2_接口层.Y4_序列化接口;
import S17_Netty实现一对一单聊.Y3_实现层.Y5_登录请求数据包;
import S17_Netty实现一对一单聊.Y3_实现层.Y6_登录响应数据包;
import S17_Netty实现一对一单聊.Y3_实现层.Y7_发送消息请求数据包;
import S17_Netty实现一对一单聊.Y3_实现层.Y8_发送消息响应数据包;
import io.netty.buffer.ByteBuf;

public class Y11_解码 {

    public static final int 魔数 = 0x135790;

    /**
     * 数据结构：
     * 4 byte : 魔数
     * 1 byte : 版本
     * 1 byte : 算法
     * 1 byte : 指令
     * 4 byte : 数据长度
     * all byte : 数据
     */
    public static <T> T 解码载体(ByteBuf 数据载体) {

        int 数据魔数 = 数据载体.readInt();
        byte 版本 = 数据载体.readByte();
        byte 算法 = 数据载体.readByte();
        byte 指令 = 数据载体.readByte();
        int 长度 = 数据载体.readInt();
        byte[] 数据 = new byte[长度];
        数据载体.readBytes(数据);

        Y4_序列化接口 默认序列化实现 = Y4_序列化接口.默认实现;

        switch (指令) {
            case Y1_指令类型.LOGIN_REQUEST:
                return (T) 默认序列化实现.反序列化(数据, Y5_登录请求数据包.class);
            case Y1_指令类型.LOGIN_RESPONSE:
                return (T) 默认序列化实现.反序列化(数据, Y6_登录响应数据包.class);
            case Y1_指令类型.SEND_MESSAGE_REQUEST:
                return (T) 默认序列化实现.反序列化(数据, Y7_发送消息请求数据包.class);
            case Y1_指令类型.SEND_MESSAGE_RESPONSE:
                return (T) 默认序列化实现.反序列化(数据, Y8_发送消息响应数据包.class);
            default:
                break;
        }

        return null;
    }

}

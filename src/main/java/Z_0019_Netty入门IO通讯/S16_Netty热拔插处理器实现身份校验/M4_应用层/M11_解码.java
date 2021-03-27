package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_应用层;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_常量.M1_指令集合;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_常量.M2_序列化算法类型;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M2_接口层.M4_序列化接口;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_实现层.M5_登录请求数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_实现层.M6_登录响应数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_实现层.M7_发送消息请求数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_实现层.M8_发送消息响应数据;
import io.netty.buffer.ByteBuf;

public class M11_解码 {

    public final static int 魔数 = 0x98765432;

    /**
     * 解码规则
     * 4 字节: 魔数
     * 1 字节: 版本
     * 1 字节: 算法类型
     * 1 字节: 指令
     * 4 字节: 数据长度
     * 余下: 序列化后的数据
     */
    public static <T> T 进行解码(ByteBuf 数据载体) {
        int 魔数 = 数据载体.readInt(); //  数据载体.skipBytes(4);
        byte 版本 = 数据载体.readByte();
        byte 算法类型 = 数据载体.readByte();
        byte 指令 = 数据载体.readByte();
        int 数据长度 = 数据载体.readInt();
        byte[] 数据 = new byte[数据长度];
        数据载体.readBytes(数据);
        M4_序列化接口 序列化实现 = null;
        if (算法类型 == M2_序列化算法类型.JSON) {
            序列化实现 = M4_序列化接口.默认序列化实现;
        }
        if (M1_指令集合.SEND_MESSAGE_REQUEST == 指令) {
            return (T) 序列化实现.反序列化(数据, M7_发送消息请求数据.class);
        } else if (M1_指令集合.SEND_MESSAGE_RESPONSE == 指令) {
            return (T) 序列化实现.反序列化(数据, M8_发送消息响应数据.class);
        } else if (M1_指令集合.LOGIN_REQUEST == 指令) {
            return (T) 序列化实现.反序列化(数据, M5_登录请求数据.class);
        } else if (M1_指令集合.LOGIN_RESPONSE == 指令) {
            return (T) 序列化实现.反序列化(数据, M6_登录响应数据.class);
        } else {
            throw new RuntimeException("未找到转型类");
        }
    }

}

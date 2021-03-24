package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V4_应用层;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V1_常量.V1_指令类型;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_接口层.V4_序列化接口;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V6_登录请求数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V7_登录响应数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V8_发送消息请求数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V9_发送消息响应数据包;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V11_解码 {

    public static <T> T 解码(ByteBuf 数据载体) {
        /*
        数据载体.writeInt(魔数);
        数据载体.writeByte(版本);
        数据载体.writeByte(算法类型);
        数据载体.writeByte(指令);
        数据载体.writeInt(序列化后数据的长度);
        数据载体.writeBytes(序列化后的数据);
        */
        数据载体.skipBytes(4);
        byte 版本 = 数据载体.readByte();
        byte 算法 = 数据载体.readByte();
        byte 指令 = 数据载体.readByte();
        int 长度 = 数据载体.readInt();
        byte[] 数据 = new byte[长度];
        数据载体.readBytes(数据);

        if (V1_指令类型.SEND_MESSAGE_REQUEST == 指令) {
            return (T) V4_序列化接口.默认实现.反序列化(数据, V8_发送消息请求数据包.class);
        } else if (V1_指令类型.SEND_MESSAGE_RESPONSE == 指令) {
            return (T) V4_序列化接口.默认实现.反序列化(数据, V9_发送消息响应数据包.class);
        } else if (V1_指令类型.LOGIN_RESPONSE == 指令) {
            return (T) V4_序列化接口.默认实现.反序列化(数据, V7_登录响应数据包.class);
        } else {
            return (T) V4_序列化接口.默认实现.反序列化(数据, V6_登录请求数据包.class);
        }
    }

}

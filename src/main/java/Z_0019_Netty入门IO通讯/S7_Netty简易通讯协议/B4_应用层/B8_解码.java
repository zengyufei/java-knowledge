package Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B4_应用层;

import Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B2_接口层.B3_抽象数据包;
import Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B2_接口层.B4_序列化接口;
import Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B3_实现层.B5_具体数据包;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class B8_解码 {

    private final static Integer 魔数 = 0x12345678;

    public static B3_抽象数据包 解码(ByteBuf 数据载体) {

        // 跳过魔数
        数据载体.skipBytes(4);
        // 跳过版本号
        数据载体.skipBytes(1);

        byte 算法类型 = 数据载体.readByte();
        byte 指令 = 数据载体.readByte();
        int 数据长度 = 数据载体.readInt();
        byte[] 数据 = new byte[数据长度];
        数据载体.readBytes(数据);

        B4_序列化接口 默认序列化方式 = B4_序列化接口.默认序列化方式;
        B3_抽象数据包 反序列化后的数据包 = 默认序列化方式.反序列化(数据, B5_具体数据包.class);

        return 反序列化后的数据包;
    }

}

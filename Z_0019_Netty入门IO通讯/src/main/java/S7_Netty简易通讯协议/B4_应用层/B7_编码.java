package S7_Netty简易通讯协议.B4_应用层;

import S7_Netty简易通讯协议.B2_接口层.B3_抽象数据包;
import S7_Netty简易通讯协议.B2_接口层.B4_序列化接口;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class B7_编码 {

    private final static Integer 魔数 = 0x12345678;

    public static ByteBuf 编码(B3_抽象数据包 数据包) {
        B4_序列化接口 默认序列化方式 = B4_序列化接口.默认序列化方式;
        byte[] 序列化后的数据 = 默认序列化方式.序列化(数据包);

        Byte 版本号 = 数据包.get版本号();
        Byte 序列化算法类型 = 默认序列化方式.序列化算法类型();
        Byte 指令 = 数据包.获取指令(); // 255 长度，255 种指令，当前项目足够
        int 数据长度 = 序列化后的数据.length;

        ByteBuf 数据载体 = ByteBufAllocator.DEFAULT.ioBuffer();
        数据载体.writeInt(魔数);
        数据载体.writeByte(版本号);
        数据载体.writeByte(序列化算法类型);
        数据载体.writeByte(指令);
        数据载体.writeInt(数据长度);
        数据载体.writeBytes(序列化后的数据);

        return 数据载体;
    }

}

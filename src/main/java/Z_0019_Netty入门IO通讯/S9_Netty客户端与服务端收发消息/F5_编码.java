package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 描述：
 * @author zengyufei
 */
public class F5_编码 {

    public static ByteBuf 编码(ByteBufAllocator 载体, F1_抽象数据包 抽象数据包) {
        F3_JSON序列化实现 json序列化实现 = F2_序列化接口.默认实现;
        byte 获取算法类型 = json序列化实现.获取算法类型();

        byte[] 序列化好的数据 = json序列化实现.序列化(抽象数据包);
        int 数据长度 = 序列化好的数据.length;

        ByteBuf 数据载体 = 载体.ioBuffer();
        数据载体.writeInt(F1_抽象数据包.魔数);
        数据载体.writeByte(抽象数据包.版本);
        数据载体.writeByte(获取算法类型);
        数据载体.writeByte(抽象数据包.指令());
        数据载体.writeInt(数据长度);
        数据载体.writeBytes(序列化好的数据);

        return 数据载体;
    }

    public static ByteBuf 本地编码(F1_抽象数据包 抽象数据包) {
        return 编码(ByteBufAllocator.DEFAULT, 抽象数据包);
    }

}

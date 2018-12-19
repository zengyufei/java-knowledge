package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_编码解码;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C1_序列化.C1_序列化接口;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C1_序列化.C2_JSON序列化实现;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包.C1_抽象数据包;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 * @author zengyufei
 */
public class C4_编码 {

    public final static int 魔数 = 0x87654321;

    public static void 编码(ByteBuf 数据载体, C1_抽象数据包 抽象数据包) {
        C2_JSON序列化实现 json序列化实现 = C1_序列化接口.默认实现;
        byte 算法类型 = json序列化实现.算法类型();
        byte[] 序列化后的数据 = json序列化实现.序列化(抽象数据包);
        byte 版本 = 抽象数据包.版本;
        byte 指令 = 抽象数据包.指令();

        int 序列化后数据的长度 = 序列化后的数据.length;

        数据载体.writeInt(魔数);
        数据载体.writeByte(版本);
        数据载体.writeByte(算法类型);
        数据载体.writeByte(指令);
        数据载体.writeInt(序列化后数据的长度);
        数据载体.writeBytes(序列化后的数据);
    }

}

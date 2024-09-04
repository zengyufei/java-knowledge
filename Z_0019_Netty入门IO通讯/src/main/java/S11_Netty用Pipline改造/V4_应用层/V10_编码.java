package S11_Netty用Pipline改造.V4_应用层;

import S11_Netty用Pipline改造.V2_接口层.V3_抽象数据包;
import S11_Netty用Pipline改造.V2_接口层.V4_序列化接口;
import S11_Netty用Pipline改造.V3_实现层.V5_JSON序列化实现;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V10_编码 {

    public static void 编码(ByteBuf 数据载体, V3_抽象数据包 抽象数据包) {
        V5_JSON序列化实现 json序列化实现 = V4_序列化接口.默认实现;
        byte 算法类型 = json序列化实现.算法类型();
        byte[] 序列化后的数据 = json序列化实现.序列化(抽象数据包);
        byte 版本 = 抽象数据包.版本;
        byte 指令 = 抽象数据包.指令();

        int 序列化后数据的长度 = 序列化后的数据.length;
        final int 魔数 = 0x12345678;

        数据载体.writeInt(魔数);
        数据载体.writeByte(版本);
        数据载体.writeByte(算法类型);
        数据载体.writeByte(指令);
        数据载体.writeInt(序列化后数据的长度);
        数据载体.writeBytes(序列化后的数据);
    }

}

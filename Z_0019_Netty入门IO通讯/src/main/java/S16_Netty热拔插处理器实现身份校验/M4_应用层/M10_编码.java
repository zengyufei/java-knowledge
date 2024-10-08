package S16_Netty热拔插处理器实现身份校验.M4_应用层;

import S16_Netty热拔插处理器实现身份校验.M2_接口层.M3_数据包抽象层;
import S16_Netty热拔插处理器实现身份校验.M2_接口层.M4_序列化接口;
import io.netty.buffer.ByteBuf;

public class M10_编码 {

    public final static int 魔数 = 0x98765432;

    /**
     * 编码规则
     * 4 字节: 魔数
     * 1 字节: 版本
     * 1 字节: 算法类型
     * 1 字节: 指令
     * 4 字节: 数据长度
     * 余下: 序列化后的数据
     */
    public static void 进行编码(ByteBuf 数据载体, M3_数据包抽象层 真实数据包) {
        M4_序列化接口 默认序列化实现 = M4_序列化接口.默认序列化实现;
        byte 序列化算法类型 = 默认序列化实现.序列化算法类型();
        byte[] 序列化后的数据 = 默认序列化实现.序列化(真实数据包);

        byte 指令类型 = 真实数据包.获取指令类型();
        byte 二进制标识数据版本 = 真实数据包.二进制标识数据版本;

        int 序列化后的数据长度 = 序列化后的数据.length;

        数据载体.writeInt(魔数);
        数据载体.writeByte(二进制标识数据版本);
        数据载体.writeByte(序列化算法类型);
        数据载体.writeByte(指令类型);
        数据载体.writeInt(序列化后的数据长度);
        数据载体.writeBytes(序列化后的数据);
    }

}

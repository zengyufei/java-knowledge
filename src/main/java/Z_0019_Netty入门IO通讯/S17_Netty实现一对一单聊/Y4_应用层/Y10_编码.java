package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y4_应用层;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_接口层.Y3_抽象数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_接口层.Y4_序列化接口;
import io.netty.buffer.ByteBuf;

public class Y10_编码 {

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
    public static void 编码塞入载体(ByteBuf 数据载体, Y3_抽象数据包 抽象数据包) {
        Y4_序列化接口 默认序列化实现 = Y4_序列化接口.默认实现;

        byte 版本 = 抽象数据包.get版本();
        byte 算法类型 = 默认序列化实现.算法类型();
        byte 指令 = 抽象数据包.指令();
        byte[] 二进制数据 = 默认序列化实现.序列化(抽象数据包);
        int 二进制数据长度 = 二进制数据.length;

        数据载体.writeInt(魔数);
        数据载体.writeByte(版本);
        数据载体.writeByte(算法类型);
        数据载体.writeByte(指令);
        数据载体.writeInt(二进制数据长度);
        数据载体.writeBytes(二进制数据);
    }

}

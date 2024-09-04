package S8_Netty实现登录.P0_简易通讯协议.P4_应用层;

import S8_Netty实现登录.P0_简易通讯协议.P2_接口层.P3_抽象数据包;
import S8_Netty实现登录.P0_简易通讯协议.P2_接口层.P4_序列化接口;
import S8_Netty实现登录.P0_简易通讯协议.P3_实现层.P6_登录请求数据包;
import S8_Netty实现登录.P0_简易通讯协议.P3_实现层.P7_登录响应数据包;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class P9_解码 {

    public static P3_抽象数据包 解码(ByteBuf 数据载体) {

        // 跳过魔数
        数据载体.skipBytes(4);

        byte 版本号 = 数据载体.readByte();
        byte 算法类型 = 数据载体.readByte();
        byte 指令 = 数据载体.readByte();
        int 数据长度 = 数据载体.readInt();
        byte[] 数据 = new byte[数据长度];
        数据载体.readBytes(数据);

        P4_序列化接口 默认序列化方式 = P4_序列化接口.默认序列化方式;
        if (版本号 == (byte) 1) {
            P6_登录请求数据包 登录请求数据包 = 默认序列化方式.反序列化(数据, P6_登录请求数据包.class);
            return 登录请求数据包;
        } else if (版本号 == (byte) 2) {
            P7_登录响应数据包 登录响应数据包 = 默认序列化方式.反序列化(数据, P7_登录响应数据包.class);
            return 登录响应数据包;
        }
        return 默认序列化方式.反序列化(数据, P3_抽象数据包.class);
    }

}

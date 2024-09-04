package S9_Netty客户端与服务端收发消息.F0_协议.F4_应用层;

import S9_Netty客户端与服务端收发消息.F0_协议.F2_接口层.F3_抽象数据包;
import S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.*;
import cn.hutool.core.lang.Console;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class F11_解码 {

    public static F3_抽象数据包 解码(ByteBuf 数据载体) {
        /*
            数据.writeInt(F1_抽象数据包.魔数);
            数据.writeByte(抽象数据包.版本);
            数据.writeInt(获取算法类型);
            数据.writeByte(抽象数据包.指令());
            数据.writeInt(数据长度);
            数据.readBytes(序列化好的数据);
        */
        数据载体.skipBytes(4);
        byte 版本 = 数据载体.readByte();
        byte 算法 = 数据载体.readByte();
        byte 指令 = 数据载体.readByte();
        int 数据长度 = 数据载体.readInt();
        byte[] bytes = new byte[数据长度];
        数据载体.readBytes(bytes);
        F3_抽象数据包 反序列化后的数据包 = null;
        switch (指令) {
            case 1:
                反序列化后的数据包 = F5_JSON序列化实现.默认实现.反序列化(bytes, F6_登录请求数据包.class);
                break;
            case 2:
                反序列化后的数据包 = F5_JSON序列化实现.默认实现.反序列化(bytes, F7_登录响应数据包.class);
                break;
            case 3:
                反序列化后的数据包 = F5_JSON序列化实现.默认实现.反序列化(bytes, F8_发送消息请求数据包.class);
                break;
            case 4:
                反序列化后的数据包 = F5_JSON序列化实现.默认实现.反序列化(bytes, F9_发送消息响应数据包.class);
                break;
        }
        return 反序列化后的数据包;
    }

    public static void main(String[] args) {
        F6_登录请求数据包 登录请求数据包 = new F6_登录请求数据包();
        登录请求数据包.set账号("admin");
        登录请求数据包.set密码("admin");
        登录请求数据包.set姓名("管理员");

        ByteBuf 编码好的数据 = F10_编码.本地编码(登录请求数据包);

        F3_抽象数据包 解码 = F11_解码.解码(编码好的数据);
        if (解码 instanceof F6_登录请求数据包) {
            Console.log("账号：{}。密码：{}。姓名：{}。指令：{}。",
                    ((F6_登录请求数据包) 解码).get账号(), ((F6_登录请求数据包) 解码).get密码(), ((F6_登录请求数据包) 解码).get姓名(), 解码.指令());
        }
    }

}

package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

import cn.hutool.core.lang.Console;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 * @author zengyufei
 */
public class F5_解码 {

    public static F1_抽象数据包 解码(ByteBuf 数据载体) {
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
        int 算法 = 数据载体.readInt();
        byte 指令 = 数据载体.readByte();
        int 数据长度 = 数据载体.readInt();
        byte[] bytes = new byte[数据长度];
        数据载体.readBytes(bytes);
        F1_抽象数据包 反序列化后的数据包 = null;
        switch (指令) {
            case 1:
                反序列化后的数据包 = F3_JSON序列化实现.默认实现.反序列化(bytes, F4_登录请求数据包.class);
                break;
            case 2:
                反序列化后的数据包 = F3_JSON序列化实现.默认实现.反序列化(bytes, F4_登录响应数据包.class);
                break;
            case 3:
                反序列化后的数据包 = F3_JSON序列化实现.默认实现.反序列化(bytes, F4_发送消息请求数据包.class);
                break;
            case 4:
                反序列化后的数据包 = F3_JSON序列化实现.默认实现.反序列化(bytes, F4_发送消息响应数据包.class);
                break;
        }
        return 反序列化后的数据包;
    }

    public static void main(String[] args) {
        F4_登录请求数据包 登录请求数据包 = new F4_登录请求数据包();
        登录请求数据包.账号 = "admin";
        登录请求数据包.密码 = "admin";
        登录请求数据包.真实姓名 = "管理员";

        ByteBuf 编码好的数据 = F5_编码.本地编码(登录请求数据包);

        F1_抽象数据包 解码 = F5_解码.解码(编码好的数据);
        if (解码 instanceof F4_登录请求数据包) {
            Console.log("账号：{}。密码：{}。姓名：{}。指令：{}。", ((F4_登录请求数据包) 解码).账号, ((F4_登录请求数据包) 解码).密码, ((F4_登录请求数据包) 解码).真实姓名, 解码.指令());
        }
    }

}

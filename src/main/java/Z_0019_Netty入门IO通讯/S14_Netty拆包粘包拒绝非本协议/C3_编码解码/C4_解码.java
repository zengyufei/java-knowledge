package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_编码解码;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C1_序列化.C1_序列化接口;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包.*;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 * @author zengyufei
 */
public class C4_解码 {

    public static <T> T 解码(ByteBuf 数据载体) {
        /*
        数据载体.writeInt(魔数);
        数据载体.writeByte(版本);
        数据载体.writeByte(算法类型);
        数据载体.writeByte(指令);
        数据载体.writeInt(序列化后数据的长度);
        数据载体.writeBytes(序列化后的数据);
        */
        数据载体.skipBytes(4);
        byte 版本 = 数据载体.readByte();
        byte 算法 = 数据载体.readByte();
        byte 指令 = 数据载体.readByte();
        int 长度 = 数据载体.readInt();
        byte[] 数据 = new byte[长度];
        数据载体.readBytes(数据);

        if(C1_指令.SEND_MESSAGE_REQUEST == 指令){
            return (T) C1_序列化接口.默认实现.反序列化(数据, C3_发送消息请求数据包.class);
        }else if(C1_指令.SEND_MESSAGE_RESPONSE == 指令){
            return (T) C1_序列化接口.默认实现.反序列化(数据, C3_发送消息响应数据包.class);
        }else if(C1_指令.LOGIN_RESPONSE == 指令){
            return (T) C1_序列化接口.默认实现.反序列化(数据, C3_登录响应数据包.class);
        }else{
            return (T) C1_序列化接口.默认实现.反序列化(数据, C3_登录请求数据包.class);
        }
    }

}

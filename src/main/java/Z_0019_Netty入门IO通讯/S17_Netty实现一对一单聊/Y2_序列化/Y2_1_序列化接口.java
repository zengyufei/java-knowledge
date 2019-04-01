package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_序列化;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y1_数据包.Y1_1_抽象数据包;

public interface Y2_1_序列化接口 {

    Y2_1_序列化接口 默认实现 = new Y2_3_JSON序列化实现();

    byte 算法类型();

    byte[] 序列化(Y1_1_抽象数据包 抽象数据包);

    <T> T 反序列化(byte[] 二进制数据, Class<T> 转型class);

}

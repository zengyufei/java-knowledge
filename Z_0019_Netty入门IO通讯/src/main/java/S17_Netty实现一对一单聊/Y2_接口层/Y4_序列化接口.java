package S17_Netty实现一对一单聊.Y2_接口层;

import S17_Netty实现一对一单聊.Y3_实现层.Y9_JSON序列化实现;

public interface Y4_序列化接口 {

    Y4_序列化接口 默认实现 = new Y9_JSON序列化实现();

    byte 算法类型();

    byte[] 序列化(Y3_抽象数据包 抽象数据包);

    <T> T 反序列化(byte[] 二进制数据, Class<T> 转型class);

}

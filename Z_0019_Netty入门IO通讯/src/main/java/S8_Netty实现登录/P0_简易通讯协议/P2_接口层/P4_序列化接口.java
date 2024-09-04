package S8_Netty实现登录.P0_简易通讯协议.P2_接口层;


import S8_Netty实现登录.P0_简易通讯协议.P3_实现层.P5_JSON序列化;

public interface P4_序列化接口 {

    P4_序列化接口 默认序列化方式 = new P5_JSON序列化();

    Byte 序列化算法类型();

    byte[] 序列化(P3_抽象数据包 抽象数据包);

    <T> T 反序列化(byte[] 字节流数据, Class<T> clazz);

}

package Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议;


public interface P5_序列化接口 {

    P5_序列化接口 默认序列化方式 = new P6_JSON序列化();

    Byte 序列化算法类型();

    byte[] 序列化(P3_抽象数据包 抽象数据包);

    <T> T 反序列化(byte[] 字节流数据, Class<T> clazz);

}

package Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议;

/**
 * 描述：
 * @author zengyufei
 */
public interface B4_序列化接口 {

    B4_序列化接口 默认序列化方式 = new B5_JSON序列化();

    Byte 序列化算法类型();

    byte[] 序列化(B1_抽象数据包 数据包);

    <T> T 反序列化(byte[] bytes, Class<T> clazz);

}

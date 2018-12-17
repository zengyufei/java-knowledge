package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

/**
 * 描述：
 * @author zengyufei
 */
public interface F2_序列化接口 {

    F3_JSON序列化实现 默认实现 = new F3_JSON序列化实现();

    int 获取算法类型();

    byte[] 序列化(F1_抽象数据包 抽象数据包);

    <T> T 反序列化(byte[] 二进制数据, Class<T> clazz);

}

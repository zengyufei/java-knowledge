package S9_Netty客户端与服务端收发消息.F0_协议.F2_接口层;

import S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F5_JSON序列化实现;

/**
 * 描述：
 *
 * @author zengyufei
 */
public interface F4_序列化接口 {

    F5_JSON序列化实现 默认实现 = new F5_JSON序列化实现();

    byte 获取算法类型();

    byte[] 序列化(F3_抽象数据包 抽象数据包);

    <T> T 反序列化(byte[] 二进制数据, Class<T> clazz);

}

package Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B2_接口层;

import Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B3_实现层.B6_JSON序列化;

/**
 * 描述：
 *
 * @author zengyufei
 */
public interface B4_序列化接口 {

    B4_序列化接口 默认序列化方式 = new B6_JSON序列化();

    Byte 序列化算法类型();

    byte[] 序列化(B3_抽象数据包 数据包);

    <T> T 反序列化(byte[] bytes, Class<T> clazz);

}

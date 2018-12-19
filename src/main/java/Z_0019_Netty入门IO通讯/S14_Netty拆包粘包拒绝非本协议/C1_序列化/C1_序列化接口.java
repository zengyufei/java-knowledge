package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C1_序列化;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包.C1_抽象数据包;

/**
 * 描述：
 * @author zengyufei
 */
public interface C1_序列化接口 {

    C2_JSON序列化实现 默认实现 = new C2_JSON序列化实现();

    byte 算法类型();

    byte[] 序列化(C1_抽象数据包 数据包);

    <T> T 反序列化(byte[] bytes, Class<T> clazz);

}

package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_接口层;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V5_JSON序列化实现;

/**
 * 描述：
 *
 * @author zengyufei
 */
public interface V4_序列化接口 {

    V5_JSON序列化实现 默认实现 = new V5_JSON序列化实现();

    byte 算法类型();

    byte[] 序列化(V3_抽象数据包 数据包);

    <T> T 反序列化(byte[] bytes, Class<T> clazz);

}

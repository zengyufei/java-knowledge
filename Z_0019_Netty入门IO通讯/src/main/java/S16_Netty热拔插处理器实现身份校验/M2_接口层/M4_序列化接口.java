package S16_Netty热拔插处理器实现身份校验.M2_接口层;

import S16_Netty热拔插处理器实现身份校验.M3_实现层.M9_JSON序列化实现;

public interface M4_序列化接口 {

    M4_序列化接口 默认序列化实现 = new M9_JSON序列化实现();

    byte 序列化算法类型();

    byte[] 序列化(M3_数据包抽象层 实际数据包);

    <T> T 反序列化(byte[] 二进制数据, Class<T> 反序列化class);

}

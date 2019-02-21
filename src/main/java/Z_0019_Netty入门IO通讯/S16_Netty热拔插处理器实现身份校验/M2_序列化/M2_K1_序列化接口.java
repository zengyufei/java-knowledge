package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M2_序列化;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K1_数据包抽象层;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M2_序列化.实现.M2_K3_JSON序列化实现;

public interface M2_K1_序列化接口 {

    M2_K1_序列化接口 默认序列化实现 = new M2_K3_JSON序列化实现();

    byte 序列化算法类型();

    byte[] 序列化(M1_K1_数据包抽象层 实际数据包);

    <T> T 反序列化(byte[] 二进制数据, Class<T> 反序列化class);

}

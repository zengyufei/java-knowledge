package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M2_序列化.实现;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K1_数据包抽象层;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M2_序列化.M2_K1_序列化接口;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M2_序列化.M2_K2_序列化算法集合;
import com.alibaba.fastjson.JSONObject;

public class M2_K3_JSON序列化实现 implements M2_K1_序列化接口 {

    @Override
    public byte 序列化算法类型() {
        return M2_K2_序列化算法集合.JSON;
    }

    @Override
    public byte[] 序列化(M1_K1_数据包抽象层 实际数据包) {
        return JSONObject.toJSONBytes(实际数据包);
    }

    @Override
    public <T> T 反序列化(byte[] 二进制数据, Class<T> 反序列化class) {
        return JSONObject.parseObject(二进制数据, 反序列化class);
    }
}

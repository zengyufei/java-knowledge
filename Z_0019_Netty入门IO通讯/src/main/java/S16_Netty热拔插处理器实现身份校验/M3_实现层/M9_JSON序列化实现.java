package S16_Netty热拔插处理器实现身份校验.M3_实现层;

import S16_Netty热拔插处理器实现身份校验.M1_常量.M2_序列化算法类型;
import S16_Netty热拔插处理器实现身份校验.M2_接口层.M3_数据包抽象层;
import S16_Netty热拔插处理器实现身份校验.M2_接口层.M4_序列化接口;
import com.alibaba.fastjson.JSONObject;

public class M9_JSON序列化实现 implements M4_序列化接口 {

    @Override
    public byte 序列化算法类型() {
        return M2_序列化算法类型.JSON;
    }

    @Override
    public byte[] 序列化(M3_数据包抽象层 实际数据包) {
        return JSONObject.toJSONBytes(实际数据包);
    }

    @Override
    public <T> T 反序列化(byte[] 二进制数据, Class<T> 反序列化class) {
        return JSONObject.parseObject(二进制数据, 反序列化class);
    }
}

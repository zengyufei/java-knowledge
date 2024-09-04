package S11_Netty用Pipline改造.V3_实现层;

import S11_Netty用Pipline改造.V1_常量.V2_序列化算法类型;
import S11_Netty用Pipline改造.V2_接口层.V3_抽象数据包;
import S11_Netty用Pipline改造.V2_接口层.V4_序列化接口;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V5_JSON序列化实现 implements V4_序列化接口 {

    @Override
    public byte 算法类型() {
        return V2_序列化算法类型.JSON;
    }

    @Override
    public byte[] 序列化(V3_抽象数据包 数据包) {
        return JSONObject.toJSONBytes(数据包);
    }

    @Override
    public <T> T 反序列化(byte[] bytes, Class<T> clazz) {
        return JSONObject.parseObject(bytes, clazz);
    }
}

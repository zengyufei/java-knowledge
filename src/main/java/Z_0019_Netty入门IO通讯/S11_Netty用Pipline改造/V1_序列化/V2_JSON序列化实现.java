package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V1_序列化;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V1_抽象数据包;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 * @author zengyufei
 */
public class V2_JSON序列化实现 implements V1_序列化接口 {

    @Override
    public byte 算法类型() {
        return V1_序列化算法类型.JSON;
    }

    @Override
    public byte[] 序列化(V1_抽象数据包 数据包) {
        return JSONObject.toJSONBytes(数据包);
    }

    @Override
    public <T> T 反序列化(byte[] bytes, Class<T> clazz) {
        return JSONObject.parseObject(bytes, clazz);
    }
}

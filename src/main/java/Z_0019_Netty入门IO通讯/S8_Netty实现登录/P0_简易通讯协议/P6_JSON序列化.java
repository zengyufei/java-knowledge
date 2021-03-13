package Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议;

import com.alibaba.fastjson.JSONObject;

public class P6_JSON序列化 implements P5_序列化接口{

    @Override
    public Byte 序列化算法类型() {
        return P5_算法类型.json;
    }

    @Override
    public byte[] 序列化(P3_抽象数据包 抽象数据包) {
        return JSONObject.toJSONBytes(抽象数据包);
    }

    @Override
    public <T> T 反序列化(byte[] 字节流数据, Class<T> clazz) {
        return JSONObject.parseObject(字节流数据, clazz);
    }
}

package S8_Netty实现登录.P0_简易通讯协议.P3_实现层;

import S8_Netty实现登录.P0_简易通讯协议.P1_常量.P2_序列化算法类型;
import S8_Netty实现登录.P0_简易通讯协议.P2_接口层.P3_抽象数据包;
import S8_Netty实现登录.P0_简易通讯协议.P2_接口层.P4_序列化接口;
import com.alibaba.fastjson.JSONObject;

public class P5_JSON序列化 implements P4_序列化接口 {

    @Override
    public Byte 序列化算法类型() {
        return P2_序列化算法类型.json;
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

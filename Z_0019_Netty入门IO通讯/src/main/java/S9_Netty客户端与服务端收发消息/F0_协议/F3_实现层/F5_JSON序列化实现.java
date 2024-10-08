package S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层;

import S9_Netty客户端与服务端收发消息.F0_协议.F1_常量.F2_序列化算法类型;
import S9_Netty客户端与服务端收发消息.F0_协议.F2_接口层.F3_抽象数据包;
import S9_Netty客户端与服务端收发消息.F0_协议.F2_接口层.F4_序列化接口;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class F5_JSON序列化实现 implements F4_序列化接口 {

    @Override
    public byte 获取算法类型() {
        return F2_序列化算法类型.JSON;
    }

    @Override
    public byte[] 序列化(F3_抽象数据包 抽象数据包) {
        return JSONObject.toJSONBytes(抽象数据包);
    }

    @Override
    public <T> T 反序列化(byte[] 二进制数据, Class<T> clazz) {
        return JSONObject.parseObject(二进制数据, clazz);
    }
}

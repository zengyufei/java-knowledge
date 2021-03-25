package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_实现层;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C1_常量.C2_序列化算法类型;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_接口层.C3_抽象数据包;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_接口层.C4_序列化接口;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class C5_JSON序列化实现 implements C4_序列化接口 {

    @Override
    public byte 算法类型() {
        return C2_序列化算法类型.JSON;
    }

    @Override
    public byte[] 序列化(C3_抽象数据包 数据包) {
        return JSONObject.toJSONBytes(数据包);
    }

    @Override
    public <T> T 反序列化(byte[] bytes, Class<T> clazz) {
        return JSONObject.parseObject(bytes, clazz);
    }
}

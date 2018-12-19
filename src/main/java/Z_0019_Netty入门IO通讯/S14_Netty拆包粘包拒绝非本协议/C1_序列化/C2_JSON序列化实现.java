package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C1_序列化;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包.C1_抽象数据包;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 * @author zengyufei
 */
public class C2_JSON序列化实现 implements C1_序列化接口 {

    @Override
    public byte 算法类型() {
        return C1_序列化算法类型.JSON;
    }

    @Override
    public byte[] 序列化(C1_抽象数据包 数据包) {
        return JSONObject.toJSONBytes(数据包);
    }

    @Override
    public <T> T 反序列化(byte[] bytes, Class<T> clazz) {
        return JSONObject.parseObject(bytes, clazz);
    }
}

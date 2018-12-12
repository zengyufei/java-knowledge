package Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议;

import com.alibaba.fastjson.JSON;

/**
 * 描述：
 * @author zengyufei
 */
public class B5_JSON序列化 implements B4_序列化接口 {

    @Override
    public Byte 序列化算法类型() {
        return B4_序列化算法类型.JSON算法;
    }

    @Override
    public byte[] 序列化(B1_抽象数据包 数据包) {
        return JSON.toJSONBytes(数据包);
    }

    @Override
    public <T> T 反序列化(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}

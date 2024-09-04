package S7_Netty简易通讯协议.B3_实现层;

import S7_Netty简易通讯协议.B1_常量.B2_序列化算法类型;
import S7_Netty简易通讯协议.B2_接口层.B3_抽象数据包;
import S7_Netty简易通讯协议.B2_接口层.B4_序列化接口;
import com.alibaba.fastjson.JSON;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class B6_JSON序列化 implements B4_序列化接口 {

    @Override
    public Byte 序列化算法类型() {
        return B2_序列化算法类型.JSON算法;
    }

    @Override
    public byte[] 序列化(B3_抽象数据包 数据包) {
        return JSON.toJSONBytes(数据包);
    }

    @Override
    public <T> T 反序列化(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}

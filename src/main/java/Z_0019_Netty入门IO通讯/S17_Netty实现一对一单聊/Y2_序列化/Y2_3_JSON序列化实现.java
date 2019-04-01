package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_序列化;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y1_数据包.Y1_1_抽象数据包;
import com.alibaba.fastjson.JSON;

public class Y2_3_JSON序列化实现 implements Y2_1_序列化接口 {

    @Override
    public byte 算法类型() {
        return Y2_2_算法类型.JSON;
    }

    @Override
    public byte[] 序列化(Y1_1_抽象数据包 抽象数据包) {
        return JSON.toJSONBytes(抽象数据包);
    }

    @Override
    public <T> T 反序列化(byte[] 二进制数据, Class<T> 转型class) {
        return JSON.parseObject(二进制数据, 转型class);
    }
}

package S17_Netty实现一对一单聊.Y3_实现层;

import S17_Netty实现一对一单聊.Y1_常量.Y2_序列化算法类型;
import S17_Netty实现一对一单聊.Y2_接口层.Y3_抽象数据包;
import S17_Netty实现一对一单聊.Y2_接口层.Y4_序列化接口;
import com.alibaba.fastjson.JSON;

public class Y9_JSON序列化实现 implements Y4_序列化接口 {

    @Override
    public byte 算法类型() {
        return Y2_序列化算法类型.JSON;
    }

    @Override
    public byte[] 序列化(Y3_抽象数据包 抽象数据包) {
        return JSON.toJSONBytes(抽象数据包);
    }

    @Override
    public <T> T 反序列化(byte[] 二进制数据, Class<T> 转型class) {
        return JSON.parseObject(二进制数据, 转型class);
    }
}

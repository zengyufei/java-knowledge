package S17_Netty实现一对一单聊.Y5_工具类;

import S17_Netty实现一对一单聊.Y4_应用层.Y12_会话;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;

public class Y13_会话工具类 {

    // 用户id 绑定 三次握手TCP请求通道
    private static final Map<String, Channel> 用户绑定集合 = new HashMap<>();
    private static final AttributeKey<Y12_会话> 取会话key = AttributeKey.valueOf("session");

    public static Map<String, Channel> get用户绑定集合() {
        return 用户绑定集合;
    }

    public static void 绑定会话(Y12_会话 会话, Channel 三次握手TCP通道) {
        用户绑定集合.put(会话.get用户id(), 三次握手TCP通道);
        三次握手TCP通道.attr(取会话key).set(会话);
    }

    public static void 取消绑定会话(Channel 三次握手TCP通道) {
        if (是否已经登录(三次握手TCP通道)) {
            用户绑定集合.remove(取出会话(三次握手TCP通道));
            三次握手TCP通道.attr(取会话key).set(null);
        }
    }

    public static Y12_会话 取出会话(Channel 三次握手TCP通道) {
        return 三次握手TCP通道.attr(取会话key).get();
    }

    public static boolean 是否已经登录(Channel 三次握手TCP通道) {
        return 三次握手TCP通道.hasAttr(取会话key);
    }

    public static Channel 获取通道(String 用户id) {
        return 用户绑定集合.get(用户id);
    }


}

package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M6_工具类包;


import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class M19_登录判断工具类 {

    public static void 设置为登录成功(Channel 上下文) {
        上下文.attr(AttributeKey.valueOf("loginFlag")).set(true);
    }

    public static boolean 判断是否登录成功(Channel 上下文) {
        return (boolean) 上下文.attr(AttributeKey.valueOf("loginFlag")).get();
    }
}

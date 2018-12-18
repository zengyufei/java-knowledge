package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;


import cn.hutool.core.convert.Convert;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 描述：
 * @author zengyufei
 */
public class F9_登录状态记录工具类 {

    public static void 记录登录状态(Channel channel) {
        channel.attr(AttributeKey.valueOf("login")).set(true);
    }

    public static boolean 是否登录(Channel channel) {
        return Convert.toBool(channel.attr(AttributeKey.valueOf("login")).get(), false);
    }

}

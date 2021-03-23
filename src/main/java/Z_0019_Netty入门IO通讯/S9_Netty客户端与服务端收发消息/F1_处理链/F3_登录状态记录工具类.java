package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F1_处理链;


import cn.hutool.core.convert.Convert;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class F3_登录状态记录工具类 {

    public static void 记录登录状态(Channel channel) {
        channel.attr(AttributeKey.valueOf("login")).set(true);
    }

    public static boolean 是否登录(Channel channel) {
        Object login = channel.attr(AttributeKey.valueOf("login")).get();
        return Convert.toBool(login, false);
    }

}

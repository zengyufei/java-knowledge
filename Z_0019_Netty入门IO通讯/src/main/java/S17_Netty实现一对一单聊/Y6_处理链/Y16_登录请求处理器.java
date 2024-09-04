package S17_Netty实现一对一单聊.Y6_处理链;

import S17_Netty实现一对一单聊.Y3_实现层.Y5_登录请求数据包;
import S17_Netty实现一对一单聊.Y3_实现层.Y6_登录响应数据包;
import S17_Netty实现一对一单聊.Y4_应用层.Y12_会话;
import S17_Netty实现一对一单聊.Y5_工具类.Y13_会话工具类;
import Z_utils.输出;
import cn.hutool.core.util.RandomUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Y16_登录请求处理器 extends SimpleChannelInboundHandler<Y5_登录请求数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, Y5_登录请求数据包 y5_登录请求数据包) throws Exception {
        输出.服务端.控制台输出("接收到登录请求!");
        Y6_登录响应数据包 y6_登录响应数据包;
        if (!校验账号密码(y5_登录请求数据包)) {
            输出.服务端.控制台输出("用户 {} 登录失败!", y5_登录请求数据包.get账号());
            y6_登录响应数据包 = new Y6_登录响应数据包(null, y5_登录请求数据包.get用户名(), "false", "账号密码错误");
        } else {
            String 用户id = RandomUtil.randomInt(1, 1000) + "";
            Y12_会话 会话 = new Y12_会话(用户id, y5_登录请求数据包.get用户名());
            Y13_会话工具类.绑定会话(会话, 上下文.channel());
            输出.服务端.控制台输出("用户 [{}] 登录成功!", y5_登录请求数据包.get用户名());
            y6_登录响应数据包 = new Y6_登录响应数据包(会话.get用户id(), y5_登录请求数据包.get用户名(), "true", "");
        }
        输出回复对方(上下文, y6_登录响应数据包);
    }

    @Override
    public void channelInactive(ChannelHandlerContext 上下文) throws Exception {
        Y13_会话工具类.取消绑定会话(上下文.channel());
    }

    private boolean 校验账号密码(Y5_登录请求数据包 y5_登录请求数据包) {
        return "admin".equalsIgnoreCase(y5_登录请求数据包.get账号()) && "admin".equalsIgnoreCase(y5_登录请求数据包.get密码());
    }

    private void 输出回复对方(ChannelHandlerContext 上下文, Y6_登录响应数据包 y6_登录响应数据包) {
        上下文.channel().writeAndFlush(y6_登录响应数据包);
    }
}

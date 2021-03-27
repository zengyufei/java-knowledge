package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_接口层.Y3_抽象数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y3_实现层.Y6_登录响应数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y4_应用层.Y12_会话;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y5_工具类.Y13_会话工具类;
import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Y17_登录响应处理器 extends SimpleChannelInboundHandler<Y6_登录响应数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, Y6_登录响应数据包 y6_登录响应数据包) throws Exception {
        if (判断登录是否成功(y6_登录响应数据包)) {
            输出.客户端.控制台("登录成功!");
            String 用户id = y6_登录响应数据包.get用户id();
            String 用户名 = y6_登录响应数据包.get用户名();
            Y12_会话 y12_会话 = new Y12_会话(用户id, 用户名);
            Y13_会话工具类.绑定会话(y12_会话, 上下文.channel());
            输出.客户端.控制台("用户id {} 用户名[{}]登录成功!", 用户id, 用户名);
        } else {
            输出.客户端.控制台("登录失败!{}", y6_登录响应数据包.get错误提示());
        }
    }

    private void 输出回复对方(ChannelHandlerContext 上下文, Y3_抽象数据包 y5_登录请求数据包) {
        上下文.channel().writeAndFlush(y5_登录请求数据包);
    }

    private boolean 判断登录是否成功(Y6_登录响应数据包 y6_登录响应数据包) {
        return Boolean.parseBoolean(y6_登录响应数据包.get成功状态());
    }

}

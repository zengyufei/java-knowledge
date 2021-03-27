package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y3_实现层.Y7_发送消息请求数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y3_实现层.Y8_发送消息响应数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y4_应用层.Y12_会话;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y5_工具类.Y13_会话工具类;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Y18_发送消息请求处理器 extends SimpleChannelInboundHandler<Y7_发送消息请求数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, Y7_发送消息请求数据包 y7_发送消息请求数据包) throws Exception {
        输出发送指定对方(上下文, y7_发送消息请求数据包);
    }

    private void 输出发送指定对方(ChannelHandlerContext 上下文, Y7_发送消息请求数据包 y7_发送消息请求数据包) {
        Y12_会话 发送方会话 = Y13_会话工具类.取出会话(上下文.channel());
        String 接收方用户id = y7_发送消息请求数据包.get对方用户id();
        Y8_发送消息响应数据包 y8_发送消息响应数据包 = new Y8_发送消息响应数据包(发送方会话.get用户id(), 接收方用户id, y7_发送消息请求数据包.get消息内容(), y7_发送消息请求数据包.get发送时间());
        Channel 接收方通道 = Y13_会话工具类.获取通道(接收方用户id);
        if (接收方通道 != null && Y13_会话工具类.是否已经登录(接收方通道)) {
            输出发送对方(接收方通道, y8_发送消息响应数据包);
        } else {
            System.err.println("[" + 接收方用户id + "] 不在线，发送失败!");
        }
    }

    private ChannelFuture 输出发送对方(Channel 对方通道, Y8_发送消息响应数据包 y8_发送消息响应数据包) {
        return 对方通道.writeAndFlush(y8_发送消息响应数据包);
    }

}

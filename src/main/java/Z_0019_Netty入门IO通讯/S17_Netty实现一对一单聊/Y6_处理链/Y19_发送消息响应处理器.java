package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y3_实现层.Y8_发送消息响应数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y4_应用层.Y12_会话;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y5_工具类.Y13_会话工具类;
import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Y19_发送消息响应处理器 extends SimpleChannelInboundHandler<Y8_发送消息响应数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, Y8_发送消息响应数据包 y8_发送消息响应数据包) throws Exception {
        Y12_会话 取出会话 = Y13_会话工具类.取出会话(上下文.channel());
        打印已收到日志(取出会话, y8_发送消息响应数据包);
    }

    private void 打印已收到日志(Y12_会话 取出会话, Y8_发送消息响应数据包 y8_发送消息响应数据包) {
        System.out.println("");
        输出.客户端.控制台("收到用户 {} 发来的消息: {}.", 取出会话.get用户名(), y8_发送消息响应数据包.get消息内容());
    }

}

package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y5_工具类.Y13_会话工具类;
import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Y20_登录认证处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        if (Y13_会话工具类.是否已经登录(上下文.channel())) {
            输出.服务端.控制台("已经登录,移除权限判断处理器.");
            上下文.pipeline().remove(this);
            super.channelRead(上下文, 消息);
        } else {
            上下文.channel().close();
        }
    }
}

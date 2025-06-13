package K004_认识Netty;

import Z_utils.输出;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class N2_服务端消息处理器 extends SimpleChannelInboundHandler<String> {

    public N2_服务端消息处理器() {
        super(false);
        输出.服务端.控制台输出("创建 N2_服务端消息处理器");
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        输出.服务端.控制台输出("新接入用户：{}", channel.id());
        final ChannelFuture channelFuture = channel.writeAndFlush("欢迎来到服务器！\n");
        // 监听事件完成事件
        // 打开注释，则关闭连接，客户端会自己关闭掉，导致消息还没来得及接收并处理，慎用
        // channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext 上下文, final String 消息) throws Exception {
        输出.服务端.控制台输出(消息);

        final Channel channel = 上下文.channel();
        final ChannelFuture channelFuture = channel.writeAndFlush("好好享受！\n");
        // 监听事件完成事件
        // 打开注释，则关闭连接，客户端会自己关闭掉，导致消息还没来得及接收并处理，慎用
        // channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}

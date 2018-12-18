package Z_0019_Netty入门IO通讯.S10_Netty处理链Pipline试用;

import Z_utils.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 描述：
 * @author zengyufei
 */
public class Q1_服务端输入逻辑处理器3 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Console.getThisClassName();
        ctx.channel().writeAndFlush(msg);
    }
}

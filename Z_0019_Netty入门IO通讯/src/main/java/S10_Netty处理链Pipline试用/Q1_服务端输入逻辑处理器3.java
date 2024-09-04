package S10_Netty处理链Pipline试用;

import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class Q1_服务端输入逻辑处理器3 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        输出.当前类简单名();
        System.out.println("-------------------------------------------");
        ctx.channel().writeAndFlush(msg);
    }
}

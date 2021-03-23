package Z_0019_Netty入门IO通讯.S10_Netty处理链Pipline试用;

import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class Q2_服务端输出逻辑处理器3 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        输出.当前类简单名();
        super.write(ctx, msg, promise);
    }

}

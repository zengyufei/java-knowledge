package Z_0019_Netty入门IO通讯.S15_NettyChannelHandler生命周期;

import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class H3_ChannelHandler生命周期打印 extends ChannelInboundHandlerAdapter {

    public H3_ChannelHandler生命周期打印() {
        super();
        输出.当前方法全名();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        输出.当前方法全名();
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        输出.当前方法全名();
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        输出.当前方法全名();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        输出.当前方法全名();
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        输出.当前方法全名();
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        输出.当前方法全名();
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        输出.当前方法全名();
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        输出.当前方法全名();
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        输出.当前方法全名();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public boolean isSharable() {
        输出.当前方法全名();
        return super.isSharable();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        输出.当前方法全名();
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        输出.当前方法全名();
        super.handlerRemoved(ctx);
    }
}

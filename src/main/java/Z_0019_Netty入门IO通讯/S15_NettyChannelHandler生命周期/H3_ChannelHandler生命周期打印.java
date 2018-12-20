package Z_0019_Netty入门IO通讯.S15_NettyChannelHandler生命周期;

import Z_utils.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 描述：
 * @author zengyufei
 */
public class H3_ChannelHandler生命周期打印 extends ChannelInboundHandlerAdapter {

    public H3_ChannelHandler生命周期打印() {
        super();
        cn.hutool.core.lang.Console.log("构造方法");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Console.getThisMethodName();
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Console.getThisMethodName();
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Console.getThisMethodName();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Console.getThisMethodName();
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Console.getThisMethodName();
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Console.getThisMethodName();
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Console.getThisMethodName();
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Console.getThisMethodName();
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Console.getThisMethodName();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public boolean isSharable() {
        Console.getThisMethodName();
        return super.isSharable();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Console.getThisMethodName();
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Console.getThisMethodName();
        super.handlerRemoved(ctx);
    }
}

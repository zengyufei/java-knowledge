package K004_认识Netty;

import Z_utils.输出;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class N3_客户端消息处理器 extends SimpleChannelInboundHandler<String> {

    final LongAdder COUNT = new LongAdder();

    public N3_客户端消息处理器() {
        super(false);
        输出.客户端.控制台输出("创建 N3_客户端消息处理器");
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext 上下文, final String 消息) throws Exception {
        输出.客户端.控制台输出(消息);

        COUNT.increment();

        if (COUNT.longValue() == 1) {
            final Channel channel = 上下文.channel();
            final ChannelFuture channelFuture = channel.writeAndFlush(StrUtil.format("你好，服务器，我是新用户「{}」\n", channel.id()));
            // 监听事件完成事件
            // 打开注释，则关闭连接，客户端会自己关闭掉，无法继续后续
            // channelFuture.addListener(ChannelFutureListener.CLOSE);
        } else if (COUNT.longValue() == 2) {
            final Channel channel = 上下文.channel();
            final ChannelFuture channelFuture = channel.writeAndFlush("好的，服务器\n");
            // 不需要交互了，则可以移除自己
            上下文.pipeline().remove(this);

            // N6_编排运行 时，必须休眠1秒，否则报错： java.io.IOException: 你的主机中的软件中止了一个已建立的连接
            // 这个错误通常发生在服务端尝试对一个已经由客户端关闭的连接进行读写时。
            // 当第一个客户端在发送完回复并关闭连接后，
            // 服务端可能正在尝试向这个客户端的 Channel 发送数据（即使你服务端没有主动发送，Netty 内部也可能有一些心跳或事件处理）。
            // 由于客户端已经单方面关闭了连接，当服务端尝试对一个已关闭的连接进行读写操作时，操作系统会返回 IOException，表明对方中止了连接。
            // 总结一句话：关闭 channel 太早太快了。
            TimeUnit.SECONDS.sleep(1);

            // 监听事件完成事件
            // 打开注释，则关闭连接，客户端会自己关闭掉
            channelFuture.addListener(ChannelFutureListener.CLOSE);

        }

    }
}

package K005_Netty_简单RPC实现;

import Z_utils.输出;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Q4_服务端消息处理器 extends SimpleChannelInboundHandler<Q2_请求参> {

    Q3_服务接口 服务接口 = new Q3_服务接口服务端实现();

    public Q4_服务端消息处理器() {
        super(false);
        输出.服务端.控制台输出("创建 Q4_服务端消息处理器");
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext 上下文, final Q2_请求参 消息) throws Exception {
        final Channel channel = 上下文.channel();

        输出.服务端.控制台输出("收到来自:{} 的请求...", channel.id());
        输出.服务端.控制台输出("请求参数:{}", 消息);

        final Q2_返回参 返回参 = 服务接口.计算加法(消息);

        输出.服务端.控制台输出("返回结果:{}", 返回参);

        final ChannelFuture channelFuture = channel.writeAndFlush(返回参);
        // 监听事件完成事件
        // 打开注释，则关闭连接，客户端会自己关闭掉，导致消息还没来得及接收并处理，慎用
        // channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}

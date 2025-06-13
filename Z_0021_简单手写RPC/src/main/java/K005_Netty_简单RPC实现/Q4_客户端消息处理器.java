package K005_Netty_简单RPC实现;

import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Q4_客户端消息处理器 extends SimpleChannelInboundHandler<Q2_返回参> {

    public Q4_客户端消息处理器() {
        super(false);
        输出.客户端.控制台输出("创建 Q4_客户端消息处理器");
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        输出.客户端.控制台输出("连接成功！");
        ctx.channel().writeAndFlush(new Q2_请求参(1, 3));
        输出.客户端.控制台输出("发送 1 + 3 给服务器计算...");
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext 上下文, final Q2_返回参 消息) throws Exception {
        final Boolean 成功状态 = 消息.get成功状态();
        final Integer 计算结果 = 消息.get计算结果();
        if (成功状态 != null && 成功状态) {
            输出.客户端.控制台输出("计算结果: {}", 计算结果);
        } else {
            输出.客户端.控制台输出("服务器告诉我计算失败了！");
        }

    }
}

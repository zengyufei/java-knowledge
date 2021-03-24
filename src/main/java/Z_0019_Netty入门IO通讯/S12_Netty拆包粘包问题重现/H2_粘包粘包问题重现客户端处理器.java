package Z_0019_Netty入门IO通讯.S12_Netty拆包粘包问题重现;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class H2_粘包粘包问题重现客户端处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext 上下文) throws Exception {
        for (int i = 0; i < 100; i++) {
            ByteBuf buffer = 上下文.alloc().buffer();
            buffer.writeBytes("发送登录请求。。。。\n".getBytes(StandardCharsets.UTF_8));
            上下文.channel().writeAndFlush(buffer);
        }
    }

}

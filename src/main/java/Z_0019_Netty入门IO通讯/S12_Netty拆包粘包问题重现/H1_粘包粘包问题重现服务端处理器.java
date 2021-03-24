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
public class H1_粘包粘包问题重现服务端处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        System.out.println(((ByteBuf) 消息).toString(StandardCharsets.UTF_8));
    }

}

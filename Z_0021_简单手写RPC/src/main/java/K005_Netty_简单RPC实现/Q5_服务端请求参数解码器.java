package K005_Netty_简单RPC实现;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Q5_服务端请求参数解码器 extends ByteToMessageDecoder {
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws Exception {
        final int 参数一 = byteBuf.readInt();
        final int 参数二 = byteBuf.readInt();

        list.add(new Q2_请求参(参数一, 参数二));
    }
}

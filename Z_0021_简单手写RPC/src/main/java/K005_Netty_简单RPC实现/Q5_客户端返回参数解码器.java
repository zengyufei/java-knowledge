package K005_Netty_简单RPC实现;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Q5_客户端返回参数解码器 extends ByteToMessageDecoder {
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws Exception {
        final boolean 成功状态 = byteBuf.readBoolean();
        final int 计算结果 = byteBuf.readInt();

        list.add(new Q2_返回参(成功状态, 计算结果));

    }
}

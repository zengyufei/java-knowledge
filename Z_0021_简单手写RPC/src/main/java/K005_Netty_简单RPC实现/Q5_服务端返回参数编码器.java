package K005_Netty_简单RPC实现;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Q5_服务端返回参数编码器 extends MessageToByteEncoder<Q2_返回参> {

    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Q2_返回参 q2_返回参, final ByteBuf byteBuf) throws Exception {
        byteBuf.writeBoolean(q2_返回参.get成功状态());
        byteBuf.writeInt(q2_返回参.get计算结果());
    }

}

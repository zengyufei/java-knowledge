package K005_Netty_简单RPC实现;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Q5_客户端请求参数编码器 extends MessageToByteEncoder<Q2_请求参> {

    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Q2_请求参 q2_请求参, final ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(q2_请求参.get参数一());
        byteBuf.writeInt(q2_请求参.get参数二());
    }

}

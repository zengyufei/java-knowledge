package S14_Netty拆包粘包拒绝非本协议.C5_处理链;

import S14_Netty拆包粘包拒绝非本协议.C4_应用层.C4_解码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class C5_解码处理器 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(C4_解码.解码(byteBuf));
    }

}

package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_编码解码.C4_解码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 描述：
 * @author zengyufei
 */
public class C5_解码处理器 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(C4_解码.解码(byteBuf));
    }

}

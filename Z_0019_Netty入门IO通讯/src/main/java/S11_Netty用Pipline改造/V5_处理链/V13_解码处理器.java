package S11_Netty用Pipline改造.V5_处理链;

import S11_Netty用Pipline改造.V4_应用层.V11_解码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V13_解码处理器 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(V11_解码.解码(byteBuf));
    }

}

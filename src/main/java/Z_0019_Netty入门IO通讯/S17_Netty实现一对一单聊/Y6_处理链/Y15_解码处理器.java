package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y4_应用层.Y11_解码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Y15_解码处理器 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext 上下文, ByteBuf 数据载体, List<Object> list) throws Exception {
        list.add(Y11_解码.解码载体(数据载体));
    }
}

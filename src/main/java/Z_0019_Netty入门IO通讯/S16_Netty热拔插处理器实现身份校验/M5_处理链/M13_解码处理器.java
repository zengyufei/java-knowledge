package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_处理链;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_应用层.M11_解码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class M13_解码处理器 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext 上下文, ByteBuf 数据载体, List<Object> list) throws Exception {
        list.add(M11_解码.进行解码(数据载体));
    }

}

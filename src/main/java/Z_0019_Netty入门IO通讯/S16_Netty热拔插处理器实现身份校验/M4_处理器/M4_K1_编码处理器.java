package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K1_数据包抽象层;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_编码解码.M3_K1_编码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class M4_K1_编码处理器 extends MessageToByteEncoder<M1_K1_数据包抽象层> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, M1_K1_数据包抽象层 真实数据包, ByteBuf 数据载体) throws Exception {
        M3_K1_编码.进行编码(数据载体, 真实数据包);
    }

}

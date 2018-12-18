package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V4_处理器;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V1_抽象数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_编码解码.V4_编码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 描述：
 * @author zengyufei
 */
public class V5_编码处理器 extends MessageToByteEncoder<V1_抽象数据包> {

    @Override
    protected void encode(ChannelHandlerContext ctx, V1_抽象数据包 抽象数据包, ByteBuf byteBuf) throws Exception {
        V4_编码.编码(byteBuf, 抽象数据包);
    }

}

package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V5_处理链;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_接口层.V3_抽象数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V4_应用层.V10_编码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V12_编码处理器 extends MessageToByteEncoder<V3_抽象数据包> {

    @Override
    protected void encode(ChannelHandlerContext ctx, V3_抽象数据包 抽象数据包, ByteBuf byteBuf) throws Exception {
        V10_编码.编码(byteBuf, 抽象数据包);
    }

}

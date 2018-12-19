package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包.C1_抽象数据包;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_编码解码.C4_编码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 描述：
 * @author zengyufei
 */
public class C5_编码处理器 extends MessageToByteEncoder<C1_抽象数据包> {

    @Override
    protected void encode(ChannelHandlerContext ctx, C1_抽象数据包 抽象数据包, ByteBuf byteBuf) throws Exception {
        C4_编码.编码(byteBuf, 抽象数据包);
    }

}

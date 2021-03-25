package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C5_处理链;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_接口层.C3_抽象数据包;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_应用层.C4_编码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class C8_错误编码处理器 extends MessageToByteEncoder<C3_抽象数据包> {

    @Override
    protected void encode(ChannelHandlerContext ctx, C3_抽象数据包 抽象数据包, ByteBuf byteBuf) throws Exception {
        // 错误
        C4_编码.错误编码(byteBuf, 抽象数据包);
    }

}

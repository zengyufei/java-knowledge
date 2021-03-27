package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_接口层.Y3_抽象数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y4_应用层.Y10_编码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Y14_编码处理器 extends MessageToByteEncoder<Y3_抽象数据包> {

    @Override
    protected void encode(ChannelHandlerContext 上下文, Y3_抽象数据包 y3_抽象数据包, ByteBuf 载体) throws Exception {
        Y10_编码.编码塞入载体(载体, y3_抽象数据包);
    }
}

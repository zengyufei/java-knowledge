package S16_Netty热拔插处理器实现身份校验.M5_处理链;

import S16_Netty热拔插处理器实现身份校验.M2_接口层.M3_数据包抽象层;
import S16_Netty热拔插处理器实现身份校验.M4_应用层.M10_编码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class M12_编码处理器 extends MessageToByteEncoder<M3_数据包抽象层> {

    @Override
    protected void encode(ChannelHandlerContext 上下文, M3_数据包抽象层 真实数据包, ByteBuf 数据载体) throws Exception {
        M10_编码.进行编码(数据载体, 真实数据包);
    }

}

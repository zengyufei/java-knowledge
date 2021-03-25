package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C5_处理链;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_应用层.C4_编码;
import cn.hutool.core.lang.Console;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class C7_拒绝非本协议以及定长解决拆包粘包处理器 extends LengthFieldBasedFrameDecoder {

    public C7_拒绝非本协议以及定长解决拆包粘包处理器() {
        super(Integer.MAX_VALUE, 7, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 过滤非本协议数据包
        int 传入魔数 = in.getInt(0);
        if (传入魔数 != C4_编码.魔数) {
            Console.log("协议匹配不上：传入 {} 解码 {}", 传入魔数, C4_编码.魔数);
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }

}

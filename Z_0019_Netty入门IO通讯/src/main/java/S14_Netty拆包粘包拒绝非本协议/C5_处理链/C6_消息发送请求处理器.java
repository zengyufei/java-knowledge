package S14_Netty拆包粘包拒绝非本协议.C5_处理链;

import S14_Netty拆包粘包拒绝非本协议.C3_实现层.C8_发送消息请求数据包;
import S14_Netty拆包粘包拒绝非本协议.C3_实现层.C9_发送消息响应数据包;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class C6_消息发送请求处理器 extends SimpleChannelInboundHandler<C8_发送消息请求数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, C8_发送消息请求数据包 发送消息请求数据包) throws Exception {
        String 消息 = 发送消息请求数据包.get消息();
        Console.log("收到一条来自客户端的消息： {}", 消息);
        消息 = 消息.replace("在吗", "在的");
        消息 = 消息.replace("吗", "");
        消息 = 消息.replace("?", "!");
        消息 = 消息.replace("？", "！");
        C9_发送消息响应数据包 发送消息响应数据包 = new C9_发送消息响应数据包();
        发送消息响应数据包.set消息(消息);
        ctx.channel().writeAndFlush(发送消息响应数据包);
    }

}

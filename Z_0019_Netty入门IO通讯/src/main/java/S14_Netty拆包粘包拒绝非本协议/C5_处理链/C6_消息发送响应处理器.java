package S14_Netty拆包粘包拒绝非本协议.C5_处理链;

import S14_Netty拆包粘包拒绝非本协议.C3_实现层.C8_发送消息请求数据包;
import S14_Netty拆包粘包拒绝非本协议.C3_实现层.C9_发送消息响应数据包;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class C6_消息发送响应处理器 extends SimpleChannelInboundHandler<C9_发送消息响应数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, C9_发送消息响应数据包 发送消息响应数据包) throws Exception {
        String 消息 = 发送消息响应数据包.get消息();
        Console.log("收到服务器回复： {}", 消息);

        Console.log("请输入要发给服务器的消息：");
        Scanner 控制台 = new Scanner(System.in);
        String 输入一行 = 控制台.nextLine();
        C8_发送消息请求数据包 发送消息请求数据包 = new C8_发送消息请求数据包();
        发送消息请求数据包.set消息(输入一行);
        ctx.channel().writeAndFlush(发送消息请求数据包);
    }
}

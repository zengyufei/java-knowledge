package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包.C3_发送消息响应数据包;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_数据包.C3_发送消息请求数据包;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

/**
 * 描述：
 * @author zengyufei
 */
public class C6_消息发送响应处理器 extends SimpleChannelInboundHandler<C3_发送消息响应数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, C3_发送消息响应数据包 发送消息响应数据包) throws Exception {
        String 消息 = 发送消息响应数据包.get消息();
        Console.log("收到服务器回复： {}", 消息);

        Console.log("请输入要发给服务器的消息：");
        Scanner 控制台 = new Scanner(System.in);
        String 输入一行 = 控制台.nextLine();
        C3_发送消息请求数据包 发送消息请求数据包 = new C3_发送消息请求数据包();
        发送消息请求数据包.set消息(输入一行);
        ctx.channel().writeAndFlush(发送消息请求数据包);
    }
}

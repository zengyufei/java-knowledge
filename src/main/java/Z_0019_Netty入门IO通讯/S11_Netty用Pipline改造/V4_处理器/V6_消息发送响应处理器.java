package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V4_处理器;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_发送消息响应数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_发送消息请求数据包;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

/**
 * 描述：
 * @author zengyufei
 */
public class V6_消息发送响应处理器 extends SimpleChannelInboundHandler<V3_发送消息响应数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3_发送消息响应数据包 发送消息响应数据包) throws Exception {
        String 消息 = 发送消息响应数据包.get消息();
        Console.log("收到服务器回复： {}", 消息);

        Console.log("请输入要发给服务器的消息：");
        Scanner 控制台 = new Scanner(System.in);
        String 输入一行 = 控制台.nextLine();
        V3_发送消息请求数据包 发送消息请求数据包 = new V3_发送消息请求数据包();
        发送消息请求数据包.set消息(输入一行);
        ctx.channel().writeAndFlush(发送消息请求数据包);
    }
}

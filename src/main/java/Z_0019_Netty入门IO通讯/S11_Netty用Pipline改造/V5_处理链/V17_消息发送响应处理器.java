package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V5_处理链;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V8_发送消息请求数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V9_发送消息响应数据包;
import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V17_消息发送响应处理器 extends SimpleChannelInboundHandler<V9_发送消息响应数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V9_发送消息响应数据包 发送消息响应数据包) throws Exception {
        String 消息 = 发送消息响应数据包.get消息();
        输出.客户端.控制台("收到服务器回复： {}", 消息);

        输出.客户端.控制台("请输入要发给服务器的消息：");
        Scanner 控制台 = new Scanner(System.in);
        String 输入一行 = 控制台.nextLine();
        V8_发送消息请求数据包 发送消息请求数据包 = new V8_发送消息请求数据包();
        发送消息请求数据包.set消息(输入一行);
        ctx.channel().writeAndFlush(发送消息请求数据包);
    }
}

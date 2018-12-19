package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V4_处理器;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_发送消息响应数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_发送消息请求数据包;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 描述：
 * @author zengyufei
 */
public class V6_消息发送请求处理器 extends SimpleChannelInboundHandler<V3_发送消息请求数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3_发送消息请求数据包 发送消息请求数据包) throws Exception {
        String 消息 = 发送消息请求数据包.get消息();
        Console.log("收到一条来自客户端的消息： {}", 消息);
        消息 = 消息.replace("在吗", "在的");
        消息 = 消息.replace("吗", "");
        消息 = 消息.replace("?", "!");
        消息 = 消息.replace("？", "！");
        V3_发送消息响应数据包 发送消息响应数据包 = new V3_发送消息响应数据包();
        发送消息响应数据包.set消息(消息);
        ctx.channel().writeAndFlush(发送消息响应数据包);
    }

}

package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V5_处理链;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V8_发送消息请求数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V3_实现层.V9_发送消息响应数据包;
import Z_utils.服务端输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V16_消息发送请求处理器 extends SimpleChannelInboundHandler<V8_发送消息请求数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V8_发送消息请求数据包 发送消息请求数据包) throws Exception {
        String 消息 = 发送消息请求数据包.get消息();
        服务端输出.控制台("收到一条来自客户端的消息： {}", 消息);
        消息 = 消息.replace("在吗", "在的");
        消息 = 消息.replace("吗", "");
        消息 = 消息.replace("?", "!");
        消息 = 消息.replace("？", "！");
        V9_发送消息响应数据包 发送消息响应数据包 = new V9_发送消息响应数据包();
        发送消息响应数据包.set消息(消息);
        ctx.channel().writeAndFlush(发送消息响应数据包);
    }

}

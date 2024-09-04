package S16_Netty热拔插处理器实现身份校验.M5_处理链;

import S16_Netty热拔插处理器实现身份校验.M3_实现层.M7_发送消息请求数据;
import S16_Netty热拔插处理器实现身份校验.M3_实现层.M8_发送消息响应数据;
import Z_utils.输出;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class M17_发送消息响应处理器 extends SimpleChannelInboundHandler<M8_发送消息响应数据> {

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, M8_发送消息响应数据 发送消息响应数据) throws Exception {
        int 消息响应次数 = 发送消息响应数据.get消息响应次数();
        String 消息体 = 发送消息响应数据.get消息体();
        输出.客户端.控制台输出("第 {} 次收到来自服务端消息如下：\n----------------------------\n{}\n----------------------------\n", 消息响应次数, 消息体);
        if (消息响应次数 == 4) {
            M7_发送消息请求数据 m7_发送消息请求数据 = new M7_发送消息请求数据(消息响应次数 + 1, "服务器，那你是男的还是女的？");
            输出回复对方(上下文, m7_发送消息请求数据);
            输出.客户端.控制台输出("发送[消息]给服务端。\n");
        } else if (消息响应次数 == 6) {
            输出.客户端.控制台输出("测试连接关闭。");
            关闭上下文(上下文);
        } else if (消息响应次数 == 0) {
            输出.客户端.控制台输出("登录超时失效，退出程序。");
            关闭上下文(上下文);
        } else {
            M7_发送消息请求数据 m7_发送消息请求数据 = new M7_发送消息请求数据(消息响应次数 + 1, "服务器，你叫什么名字？");
            输出回复对方(上下文, m7_发送消息请求数据);
            输出.客户端.控制台输出("发送[消息]给服务端。\n");
        }
    }

    private ChannelFuture 输出回复对方(ChannelHandlerContext 上下文, M7_发送消息请求数据 m7_发送消息请求数据) {
        return 上下文.channel().writeAndFlush(m7_发送消息请求数据);
    }

    private void 关闭上下文(ChannelHandlerContext 上下文) {
        上下文.channel().flush();
        上下文.flush();
        上下文.channel().close();
        上下文.close();
        上下文.disconnect();
        上下文.executor().shutdownGracefully();
    }
}

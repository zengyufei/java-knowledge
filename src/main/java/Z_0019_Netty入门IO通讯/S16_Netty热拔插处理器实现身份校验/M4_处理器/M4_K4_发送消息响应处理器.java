package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K4_发送消息响应数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K4_发送消息请求数据;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class M4_K4_发送消息响应处理器 extends SimpleChannelInboundHandler<M1_K4_发送消息响应数据> {

    @Override
    protected void channelRead0(ChannelHandlerContext 通道处理上下文, M1_K4_发送消息响应数据 发送消息响应数据) throws Exception {
        int 消息响应次数 = 发送消息响应数据.get消息响应次数();
        String 消息体 = 发送消息响应数据.get消息体();
        Console.log("第 {} 次收到来自服务端消息如下：\n----------------------------\n{}\n----------------------------\n", 消息响应次数, 消息体);

        if(消息响应次数 == 4) {
            通道处理上下文.channel().writeAndFlush(new M1_K4_发送消息请求数据(消息响应次数 + 1,"服务器，那你是男的还是女的？"));
            Console.log("发送[消息]给服务端。\n");
        }else if(消息响应次数 == 6) {
            Console.log("测试连接关闭。");
            通道处理上下文.channel().flush();
            通道处理上下文.flush();
            通道处理上下文.channel().close();
            通道处理上下文.close();
            通道处理上下文.disconnect();
            通道处理上下文.executor().shutdownGracefully();
        }else if(消息响应次数 == 0) {
            Console.log("登录超时失效，退出程序。");
            通道处理上下文.channel().flush();
            通道处理上下文.flush();
            通道处理上下文.channel().close();
            通道处理上下文.close();
            通道处理上下文.disconnect();
            通道处理上下文.executor().shutdownGracefully();
        }else{
            通道处理上下文.channel().writeAndFlush(new M1_K4_发送消息请求数据(消息响应次数 + 1,"服务器，你叫什么名字？"));
            Console.log("发送[消息]给服务端。\n");
        }


    }

}

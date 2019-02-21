package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K4_发送消息响应数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K4_发送消息请求数据;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class M4_K4_发送消息请求处理器 extends SimpleChannelInboundHandler<M1_K4_发送消息请求数据> {

    @Override
    protected void channelRead0(ChannelHandlerContext 通道处理上下文, M1_K4_发送消息请求数据 发送消息请求数据) throws Exception {
        int 消息请求次数 = 发送消息请求数据.get消息请求次数();
        String 消息体 = 发送消息请求数据.get消息体();

        if (消息请求次数 == 3) {
            Console.log("第 {} 次收到来自客户端消息如下：\n----------------------------\n{}\n----------------------------\n", 消息请求次数, 消息体);

            /*
            * 与 {@link Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器.M5_K5_登录状态验证处理器.java:15} 互斥
            */
            通道处理上下文.channel().attr(AttributeKey.valueOf("loginFlag")).set(false);

            通道处理上下文.channel().writeAndFlush(new M1_K4_发送消息响应数据(消息请求次数 + 1, "我叫 make。"));
        } else if (消息请求次数 == 5) {
            //throw new RuntimeException("如果此处报错运行，则说明 测试登录失效失败");
            Console.log("第 {} 次收到来自客户端消息如下：\n----------------------------\n{}\n----------------------------\n", 消息请求次数, 消息体);
            Console.log("返回[消息已收到]给客户端。\n");
            通道处理上下文.channel().writeAndFlush(new M1_K4_发送消息响应数据(消息请求次数 + 1, "关你屁事。"));
        } else {
            Console.log("第 {} 次收到来自客户端消息如下：\n----------------------------\n{}\n----------------------------\n", 消息请求次数, 消息体);
            Console.log("返回[消息已收到]给客户端。\n");
            通道处理上下文.channel().writeAndFlush(new M1_K4_发送消息响应数据(消息请求次数 + 1, "服务端已收到您的消息。"));
        }

    }

}

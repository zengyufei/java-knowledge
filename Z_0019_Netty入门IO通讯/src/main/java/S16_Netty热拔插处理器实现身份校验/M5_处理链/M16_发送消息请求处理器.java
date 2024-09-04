package S16_Netty热拔插处理器实现身份校验.M5_处理链;

import S16_Netty热拔插处理器实现身份校验.M3_实现层.M7_发送消息请求数据;
import S16_Netty热拔插处理器实现身份校验.M3_实现层.M8_发送消息响应数据;
import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class M16_发送消息请求处理器 extends SimpleChannelInboundHandler<M7_发送消息请求数据> {

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, M7_发送消息请求数据 发送消息请求数据) throws Exception {
        int 消息请求次数 = 发送消息请求数据.get消息请求次数();
        String 消息体 = 发送消息请求数据.get消息体();

        M8_发送消息响应数据 m8_发送消息响应数据;
        if (消息请求次数 == 3) {
            输出.服务端.控制台输出("第 {} 次收到来自客户端消息如下：\n----------------------------\n{}\n----------------------------\n", 消息请求次数, 消息体);
            /*
             * 与 {@link S16_Netty热拔插处理器实现身份校验.M4_处理器.M5_K5_登录状态验证处理器.java:15} 互斥
             */
            上下文.channel().attr(AttributeKey.valueOf("loginFlag")).set(false);
            m8_发送消息响应数据 = new M8_发送消息响应数据(消息请求次数 + 1, "我叫 make。");
        } else if (消息请求次数 == 5) {
            //throw new RuntimeException("如果此处报错运行，则说明 测试登录失效失败");
            输出.服务端.控制台输出("第 {} 次收到来自客户端消息如下：\n----------------------------\n{}\n----------------------------\n", 消息请求次数, 消息体);
            输出.服务端.控制台输出("返回[消息已收到]给客户端。\n");
            m8_发送消息响应数据 = new M8_发送消息响应数据(消息请求次数 + 1, "关你屁事。");
        } else {
            输出.服务端.控制台输出("第 {} 次收到来自客户端消息如下：\n----------------------------\n{}\n----------------------------\n", 消息请求次数, 消息体);
            输出.服务端.控制台输出("返回[消息已收到]给客户端。\n");
            m8_发送消息响应数据 = new M8_发送消息响应数据(消息请求次数 + 1, "服务端已收到您的消息。");
        }
        输出回复对方(上下文, m8_发送消息响应数据);

    }

    private void 输出回复对方(ChannelHandlerContext 上下文, M8_发送消息响应数据 m8_发送消息响应数据) {
        上下文.channel().writeAndFlush(m8_发送消息响应数据);
    }

}

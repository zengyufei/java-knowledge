package S5_Netty双向通讯.P1_处理链;

import Z_utils.输出;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class P1_服务端逻辑处理器 extends ChannelInboundHandlerAdapter {

    public P1_服务端逻辑处理器() {
        输出.服务端.控制台输出("实例化 P3_服务端逻辑处理器.java");
    }

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        // 1: 收到消息
        处理接收的消息((ByteBuf) 消息);
        // 2: 回复
        回复对方(上下文);
    }

    private void 处理接收的消息(ByteBuf 消息) {
        ByteBuf 接收的数据 = 消息;
        输出.服务端.控制台输出("来自客户端连接时发送的数据：" + 接收的数据.toString(StandardCharsets.UTF_8));
    }

    private void 回复对方(ChannelHandlerContext 上下文) {
        ByteBuf 回复的数据 = 上下文.alloc().buffer();
        回复的数据.writeBytes("你好，上帝客户！".getBytes(StandardCharsets.UTF_8));
        上下文.writeAndFlush(回复的数据);
    }

}

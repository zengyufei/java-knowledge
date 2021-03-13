package Z_0019_Netty入门IO通讯.S5_Netty双向通讯;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class P3_服务端逻辑处理器 extends ChannelInboundHandlerAdapter {
    
    public P3_服务端逻辑处理器() {
        System.out.println("实例化 P3_服务端逻辑处理器.java");
    }
    
    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        // 1: 收到消息
        ByteBuf 接收的数据 = (ByteBuf) 消息;
        System.out.println("服务端接收到来自客户端连接时发送的数据：" + 接收的数据.toString(Charset.forName("utf-8")));

        // 2: 回复
        ByteBuf 回复的数据 = 上下文.alloc().buffer();
        回复的数据.writeBytes("你好，上帝客户！".getBytes(StandardCharsets.UTF_8));
        上下文.writeAndFlush(回复的数据);
    }
}

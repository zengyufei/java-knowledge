package Z_0019_Netty入门IO通讯.S5_Netty双向通讯;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class P4_客户端逻辑处理器 extends ChannelInboundHandlerAdapter {

    /**
     * 连接上时执行
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 1: 获取数据
        ByteBuf 要发送的数据 = ctx.alloc().buffer();
        // 2: 填充要发送的数据
        要发送的数据.writeBytes("你好，服务器！".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(要发送的数据);
        System.out.println(new Date() + ": 客户端写出数据");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf 接收的数据 = (ByteBuf) msg;
        System.out.println("来自服务端的消息： " + 接收的数据.toString(Charset.forName("utf-8")));
    }
}

package Z_0019_Netty入门IO通讯.S5_Netty双向通讯;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class P4_客户端逻辑处理器 extends ChannelInboundHandlerAdapter {

    static boolean isEnd = false;
    
    public P4_客户端逻辑处理器() {
        System.out.println("实例化 P4_客户端逻辑处理器.java");
    }
    
    /**
     * 连接上时执行
     */
    @Override
    public void channelActive(ChannelHandlerContext 上下文) throws Exception {
        // 1: 获取数据
        ByteBuf 要发送的数据 = 上下文.alloc().buffer();
        // 2: 填充要发送的数据
        要发送的数据.writeBytes("你好，服务器！".getBytes(StandardCharsets.UTF_8));
        上下文.channel().writeAndFlush(要发送的数据);
        System.out.println(DateUtil.now() + ": 客户端写出数据");
    }

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        ByteBuf 接收的数据 = (ByteBuf) 消息;
        System.out.println("来自服务端的消息： " + 接收的数据.toString(StandardCharsets.UTF_8));
        if (!isEnd) {
            ByteBuf 承载数据容器 = 上下文.alloc().buffer();
            承载数据容器.writeBytes("很高兴与你对接, 我是你的客户端.".getBytes(StandardCharsets.UTF_8));
            上下文.writeAndFlush(承载数据容器);
            isEnd = true;
        }
    }
    
    
}

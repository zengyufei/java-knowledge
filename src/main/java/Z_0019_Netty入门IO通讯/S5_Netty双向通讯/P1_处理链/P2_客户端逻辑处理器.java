package Z_0019_Netty入门IO通讯.S5_Netty双向通讯.P1_处理链;

import Z_utils.输出;
import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class P2_客户端逻辑处理器 extends ChannelInboundHandlerAdapter {

    static boolean isEnd = false;

    public P2_客户端逻辑处理器() {
        输出.客户端.控制台("实例化 P4_客户端逻辑处理器.java");
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
        输出.客户端.控制台(DateUtil.now() + ": 客户端发送数据");
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
        输出.客户端.控制台("来自服务端的消息：" + 接收的数据.toString(StandardCharsets.UTF_8));
    }

    private void 回复对方(ChannelHandlerContext 上下文) {
        if (!isEnd) {
            ByteBuf 承载数据容器 = 上下文.alloc().buffer();
            承载数据容器.writeBytes("最后一次对话, 很高兴与你对接, 我是你的客户端.".getBytes(StandardCharsets.UTF_8));
            上下文.writeAndFlush(承载数据容器);
            isEnd = true;
        }
    }

}

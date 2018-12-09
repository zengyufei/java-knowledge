package Z_0019_Netty入门IO通讯.S3_Netty编程实现;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class N1_服务端 {

    public static void main(String[] args) {
        ServerBootstrap 服务端启动器 = new ServerBootstrap();
        final NioEventLoopGroup 处理新连接子线程组 = new NioEventLoopGroup();
        final NioEventLoopGroup 处理读写子线程组 = new NioEventLoopGroup();

        final Class<NioServerSocketChannel> 工作模式 = NioServerSocketChannel.class;

        ChannelInitializer<NioSocketChannel> 处理逻辑 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                final StringDecoder 解码器 = new StringDecoder();
                final SimpleChannelInboundHandler<String> 消息处理器 = new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                        System.out.println(msg);
                    }
                };
                ch.pipeline().addLast(解码器);
                ch.pipeline().addLast(消息处理器);
            }
        };

        服务端启动器
                .group(处理新连接子线程组, 处理读写子线程组)
                .channel(工作模式)
                .childHandler(处理逻辑)
                .bind(8000);
    }

}

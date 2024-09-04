package S8_Netty实现登录;

import S8_Netty实现登录.P1_处理链.P2_客户端逻辑处理器;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 要实现的功能是：客户端连接成功之后，向服务端写一段数据 ，服务端收到数据之后打印，并向客户端回一段数据
 */
public class P4_客户端 {

    public static void main(String[] args) {
        运行();
    }

    public static void 运行() {
        NioEventLoopGroup 读写线程组 = new NioEventLoopGroup();
        Class<NioSocketChannel> 套接字类型 = NioSocketChannel.class;
        Bootstrap 启动器 = new Bootstrap();
        启动器
                .group(读写线程组)
                .channel(套接字类型)
                .handler(管道工厂);
        启动器.connect("127.0.0.1", 8000);
    }

    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<NioSocketChannel> 管道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel nioSocketChannel) {
            nioSocketChannel.pipeline().addLast(new P2_客户端逻辑处理器());
        }
    };

}

package Z_0019_Netty入门IO通讯.S10_Netty处理链Pipline试用;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 要实现的功能是：客户端连接成功之后，向服务端写一段数据 ，服务端收到数据之后打印，并向客户端回一段数据
 */
public class Q3_服务端 {

    /*
     * 单线程模型 (单Reactor单线程)
     * 多线程模型 (单Reactor多线程)
     * 主从多线程模型 (多Reactor多线程)
     * https://juejin.im/post/5dac6ef75188252bc1657ead
     * */

    public static void main(String[] args) {
        运行();
    }

    public static void 运行() {
        final NioEventLoopGroup boss线程组 = new NioEventLoopGroup();
        final NioEventLoopGroup work线程组 = new NioEventLoopGroup();
        final Class<NioServerSocketChannel> 套接字类型 = NioServerSocketChannel.class;
        final ServerBootstrap 启动器 = new ServerBootstrap();
        启动器
                .group(boss线程组, work线程组)
                .channel(套接字类型)
                .childHandler(管道工厂);

        启动器.bind(8000).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[8000]绑定成功!");
            } else {
                System.out.println("端口绑定失败！");
            }
        });
    }

    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<NioSocketChannel> 管道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel nioSocketChannel) {
            // 输入: 自上而下
            nioSocketChannel.pipeline().addLast(new Q1_服务端输入逻辑处理器1());
            nioSocketChannel.pipeline().addLast(new Q1_服务端输入逻辑处理器2());
            nioSocketChannel.pipeline().addLast(new Q1_服务端输入逻辑处理器3());

            // 输出: 自下而上, 所以要倒序 addList
            nioSocketChannel.pipeline().addLast(new Q2_服务端输出逻辑处理器3());
            nioSocketChannel.pipeline().addLast(new Q2_服务端输出逻辑处理器2());
            nioSocketChannel.pipeline().addLast(new Q2_服务端输出逻辑处理器1());
        }
    };

}

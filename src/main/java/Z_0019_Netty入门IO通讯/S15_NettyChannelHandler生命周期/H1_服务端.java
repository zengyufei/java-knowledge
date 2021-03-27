package Z_0019_Netty入门IO通讯.S15_NettyChannelHandler生命周期;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C5_处理链.C5_编码处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C5_处理链.C5_解码处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C5_处理链.C6_登录请求处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C5_处理链.C7_拒绝非本协议以及定长解决拆包粘包处理器;
import Z_utils.输出;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 要实现的功能是：客户端连接成功之后，向服务端写一段数据 ，服务端收到数据之后打印，并向客户端回一段数据
 */
public class H1_服务端 {

    public static void main(String[] args) {
        运行();
    }

    public static void 运行() {
        final NioEventLoopGroup boss线程组 = new NioEventLoopGroup();
        final NioEventLoopGroup work线程组 = new NioEventLoopGroup();
        final Class<NioServerSocketChannel> 套接字类型 = NioServerSocketChannel.class;
        final ServerBootstrap 启动器 = new ServerBootstrap();
        启动器
                //设置线程池 前者用来处理accept事件，后者用于处理已经建立的连接的io
                .group(boss线程组, work线程组)
                .channel(套接字类型)
                .childHandler(通道工厂)
                .bind(8000)
                .addListener(监听端口绑定);
    }

    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<NioSocketChannel> 通道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel nioSocketChannel) {
            nioSocketChannel.pipeline().addLast(new C7_拒绝非本协议以及定长解决拆包粘包处理器());
            nioSocketChannel.pipeline().addLast(new H3_ChannelHandler生命周期打印());
            nioSocketChannel.pipeline().addLast(new C5_解码处理器());
            nioSocketChannel.pipeline().addLast(new C6_登录请求处理器());
            nioSocketChannel.pipeline().addLast(new C5_编码处理器());
        }
    };

    private static final GenericFutureListener<Future<? super Void>> 监听端口绑定 = 端口绑定结果 ->
            输出.服务端.控制台(端口绑定结果.isSuccess() ? "端口绑定成功！" : "端口绑定失败！");
}

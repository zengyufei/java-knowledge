package Z_0019_Netty入门IO通讯.S4_NettyAPI入门练习;

import Z_utils.输出;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class K1_服务端 {

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
        启动器.attr(AttributeKey.valueOf("给主线程设置值"), "你好，主线程");
        启动器.childAttr(AttributeKey.valueOf("给子线程设置值"), "你好，child 子线程");
        启动器
                //设置线程池 前者用来处理accept事件，后者用于处理已经建立的连接的io
                .group(boss线程组, work线程组)
                .channel(套接字类型)
                .handler(主线程管道工厂)
                .childHandler(子线程管道工厂)
                .bind(8000)
                .addListener(监听端口绑定);
    }

    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<NioServerSocketChannel> 主线程管道工厂 = new ChannelInitializer<NioServerSocketChannel>() {
        @Override
        protected void initChannel(NioServerSocketChannel nioServerSocketChannel) {
            输出.服务端.控制台("主线程启动了 Netty！");
            Attribute<Object> 给主线程的值 = nioServerSocketChannel.attr(AttributeKey.valueOf("给主线程设置值"));
            if (给主线程的值.get() != null) {
                输出.服务端.控制台("读取到给主线程的预设值： {}", 给主线程的值.get());
            }
        }
    };
    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<NioSocketChannel> 子线程管道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel nioSocketChannel) {
            输出.服务端.控制台("子线程处理读写线程切换到本线程！");
            Attribute<Object> 给子线程的值 = nioSocketChannel.attr(AttributeKey.valueOf("给子线程设置值"));
            if (给子线程的值.get() != null) {
                输出.服务端.控制台("读取到给子线程的预设值： " + 给子线程的值.get());
            }
        }
    };
    // 绑定端口返回结果
    private static final GenericFutureListener<Future<? super Void>> 监听端口绑定 = 端口绑定结果 ->
            输出.服务端.控制台(端口绑定结果.isSuccess() ? "端口绑定成功！" : "端口绑定失败！");

}

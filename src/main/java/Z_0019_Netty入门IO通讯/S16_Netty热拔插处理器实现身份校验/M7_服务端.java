package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_处理链.*;
import Z_utils.输出;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class M7_服务端 {

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

    private static final ChannelInitializer<NioSocketChannel> 通道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
            nioSocketChannel.pipeline().addLast(new M13_解码处理器());
            nioSocketChannel.pipeline().addLast(new M14_登录请求处理器());
            nioSocketChannel.pipeline().addLast(new M18_登录状态验证处理器());
            nioSocketChannel.pipeline().addLast(new M16_发送消息请求处理器());
            nioSocketChannel.pipeline().addLast(new M12_编码处理器());
        }
    };

    private static final GenericFutureListener<Future<? super Void>> 监听端口绑定 = 端口绑定结果 ->
            输出.服务端.控制台(端口绑定结果.isSuccess() ? "端口绑定成功！" : "端口绑定失败！");
}

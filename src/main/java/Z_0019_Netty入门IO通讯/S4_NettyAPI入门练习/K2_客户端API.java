package Z_0019_Netty入门IO通讯.S4_NettyAPI入门练习;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class K2_客户端API {

    private static int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup 读写线程组 = new NioEventLoopGroup();
        Bootstrap 启动器 = new Bootstrap();

        // 还有 BIO 模型：OioSocketChannel
        Class<NioSocketChannel> 通道类型 = NioSocketChannel.class;
        ChannelInitializer<NioSocketChannel> 处理逻辑 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                System.out.println("子线程处理读写线程切换到本线程！");
            }
        };

        //=========================================================================================================
        //=========================================================================================================
        //=========================================================================================================

        启动器.attr(AttributeKey.valueOf("给主线程设置值"), "你好，主线程");

        启动器
                .group(读写线程组)
                .channel(通道类型)
                .handler(处理逻辑);

        //失败重连逻辑(启动器, "127.0.0.1", 8000);
        失败重试重连逻辑(启动器, "127.0.0.1", 8000, MAX_RETRY, 2);

    }

    /**
     * 指定重试次数的连接
     */
    private static void 失败重试重连逻辑(Bootstrap 启动器, String host, int port, int retry, int delay) {
        GenericFutureListener<Future<? super Void>> 连接服务器监听器 = 连接结果 -> {
            EventLoopGroup group = 启动器.config().group();
            if (连接结果.isSuccess()) {
                System.out.println("连接成功！");
            } else if (retry == 0) {
                System.out.println("连接不上，全局退出！");
                group.shutdownGracefully();
            } else {
                int order = MAX_RETRY - retry;
                System.err.println(new Date() + ": 连接失败，第" + ++order + "次重连……");
                // 重连
                group.schedule(() -> 失败重试重连逻辑(启动器, host, port, retry - 1, delay), delay, TimeUnit.SECONDS);
            }
        };
        启动器.connect(host, port).addListener(连接服务器监听器);
    }
    
}

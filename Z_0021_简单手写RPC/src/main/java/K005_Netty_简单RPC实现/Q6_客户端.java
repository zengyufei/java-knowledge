package K005_Netty_简单RPC实现;

import Z_utils.输出;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class Q6_客户端 extends Thread {

    public static void main(String[] args) throws InterruptedException {
        new Q6_客户端().start();
    }

    @Override
    public void run() {
        final NioEventLoopGroup 负责工作的线程组 = new NioEventLoopGroup();
        final Class<NioSocketChannel> Netty类型Client = NioSocketChannel.class;
        final Bootstrap 客户端启动器 = new Bootstrap();
        try {
            final ChannelFuture channelFuture = 客户端启动器
                    // 设置线程池
                    .group(负责工作的线程组)
                    // 设置socket工厂
                    .channel(Netty类型Client)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 设置管道工厂
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(final Channel 通道) throws Exception {

                            // 打印日志
                            // 通道.pipeline().addLast(new LoggingHandler(LogLevel.INFO));

                            通道.pipeline().addLast(new Q5_客户端返回参数解码器());

                            通道.pipeline().addLast(new Q5_客户端请求参数编码器());

                            // 每个 channel 都会 new Q4_客户端消息处理器() 一次
                            通道.pipeline().addLast(new Q4_客户端消息处理器());
                        }
                    })
                    .connect(Q1_常量.地址, Q1_常量.端口)
                    // 连接到服务器并同步等待连接成功
                    // connect() 返回的 ChannelFuture 代表了连接操作
                    .sync();

            // 连接成功后打印消息
            if (channelFuture.isSuccess()) {
                输出.客户端.控制台输出("客户端已成功连接到服务器: " + Q1_常量.地址 + ":" + Q1_常量.端口);
            } else {
                输出.客户端.控制台输出("客户端连接失败: " + channelFuture.cause().getMessage());
                return; // 连接失败就退出
            }

            // 等待客户端 Channel 关闭 (这通常是应用程序的生命周期结束)
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } catch (Exception e) {
            输出.客户端.控制台输出("客户端运行时发生错误: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            // 确保线程组在程序关闭时优雅地停止
            负责工作的线程组.shutdownGracefully();
            输出.客户端.控制台输出("客户端线程组已关闭。");
        }
    }

}

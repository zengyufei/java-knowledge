package K004_认识Netty;

import Z_utils.输出;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class N4_服务端 extends Thread {

    public static void main(String[] args) throws InterruptedException {
        new N4_服务端().start();
    }

    @Override
    public void run() {
        final NioEventLoopGroup 负责接待的线程组 = new NioEventLoopGroup();
        final NioEventLoopGroup 负责工作的线程组 = new NioEventLoopGroup();
        final Class<NioServerSocketChannel> Netty类型Server = NioServerSocketChannel.class;
        final ServerBootstrap 服务端启动器 = new ServerBootstrap();
        try {
            final ChannelFuture channelFuture = 服务端启动器
                    // 设置线程池 前者用来处理accept事件，后者用于处理已经建立的连接的io
                    .group(负责接待的线程组, 负责工作的线程组)
                    // 设置socket工厂
                    .channel(Netty类型Server)
                    // 打印日志
                    // .handler(new LoggingHandler(LogLevel.INFO))
                    // 设置管道工厂
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(final Channel 通道) throws Exception {
                            // 添加解码器和编码器
                            // 1. LineBasedFrameDecoder: 解决 TCP 粘包/拆包问题，按行（\n）读取数据
                            通道.pipeline().addLast(new LineBasedFrameDecoder(1024)); // 最大长度1024字节

                            /*
                             * SimpleChannelInboundHandler<String> 期望处理的是 String 类型的消息。
                             * 然而，Netty 在底层传输的是字节流 (ByteBuf)。
                             * 为了让 String 类型的消息能够在网络上传输，并让接收方识别为 String，你需要：
                             * 编码器 (Encoder): 在发送方，将 String 编码成 ByteBuf。
                             * 解码器 (Decoder): 在接收方，将接收到的 ByteBuf 解码成 String。
                             */

                            // 2. StringDecoder: 将 ByteBuf 解码为 String (客户端发送到服务端)
                            通道.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));

                            // 3. StringEncoder: 将 String 编码为 ByteBuf (服务端发送到客户端)
                            通道.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));


                            // 给予每个 channel 都 new N2_服务端消息处理器() 一次
                            // 如果可以复用，N2_服务端消息处理器.java 可以制作成单例
                            通道.pipeline().addLast(new N2_服务端消息处理器());
                        }
                    })
                    // 这个参数影响的是还没有被accept 取出的连接
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 这个参数只是过一段时间内客户端没有响应，服务端会发送一个 ack 包，以判断客户端是否还活着。
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 绑定端口8000
                    .bind(N1_常量.端口)
                    // 绑定端口并同步等待成功
                    // bind() 返回的 ChannelFuture 代表了绑定操作
                    .sync(); // 使用 sync() 阻塞直到绑定完成

            // 绑定成功后打印消息
            if (channelFuture.isSuccess()) {
                输出.服务端.控制台输出("服务端已成功启动并监听端口: " + N1_常量.端口);
            } else {
                输出.服务端.控制台输出("服务端启动失败: " + channelFuture.cause().getMessage());
                return; // 启动失败就退出
            }

            // 等待服务器 Channel 关闭 (这通常是应用程序的生命周期结束)
            channelFuture
                    .channel()
                    .closeFuture()
                    .syncUninterruptibly();
        } catch (Exception e) {
            输出.服务端.控制台输出("服务端运行时发生错误: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            // 确保线程组在程序关闭时优雅地停止
            负责接待的线程组.shutdownGracefully();
            负责工作的线程组.shutdownGracefully();
            输出.服务端.控制台输出("服务端线程组已关闭。");
        }
    }

}

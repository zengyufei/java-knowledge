package K004_认识Netty;

import Z_utils.输出;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


public class N5_客户端_副本 extends Thread {

    public static void main(String[] args) throws InterruptedException {
        new N5_客户端_副本().start();
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
                    // 设置管道工厂
                    .handler(new ChannelInitializer<>() {
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

                            // 2. StringDecoder: 将 ByteBuf 解码为 String (服务端发送到客户端)
                            通道.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));

                            // 3. StringEncoder: 将 String 编码为 ByteBuf (客户端发送到服务端)
                            通道.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));


                            // 每次个新连接接入，都会创建一次
                            通道.pipeline().addLast(new N3_客户端消息处理器());
                        }
                    })
                    .connect(N1_常量.地址, N1_常量.端口)
                    // 连接到服务器并同步等待连接成功
                    // connect() 返回的 ChannelFuture 代表了连接操作
                    .sync();

            // 连接成功后打印消息
            if (channelFuture.isSuccess()) {
                输出.客户端.控制台输出("客户端已成功连接到服务器: " + N1_常量.地址 + ":" + N1_常量.端口);
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

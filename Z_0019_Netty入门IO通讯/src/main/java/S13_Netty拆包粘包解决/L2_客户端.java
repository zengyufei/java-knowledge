package S13_Netty拆包粘包解决;

import S12_Netty拆包粘包问题重现.H2_粘包粘包问题重现客户端处理器;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 要实现的功能是：客户端连接成功之后，向服务端写一段数据 ，服务端收到数据之后打印，并向客户端回一段数据
 */
public class L2_客户端 {

    /*
     * netty提供了以下三种方式解决TCP粘包和拆包问题：
     * DelimiterBasedFrameDecoder：分隔符。
     * LineBasedFrameDecoder：结束符\n。
     * FixedLengthFrameDecoder：固定长度。
     * LengthFieldBasedFrameDecoder+LengthFieldPrepender：自定义消息长度
     */
    public static void main(String[] args) {
        运行();
    }

    public static void 运行() {
        final NioEventLoopGroup 数据读写子线程组 = new NioEventLoopGroup();
        final Class<NioSocketChannel> 套接字类型 = NioSocketChannel.class;
        final Bootstrap 启动器 = new Bootstrap();
        启动器
                .group(数据读写子线程组)
                .channel(套接字类型)
                .handler(通道工厂);
        启动器.connect("127.0.0.1", 8000);
    }

    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<NioSocketChannel> 通道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel nioSocketChannel) {
            nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            nioSocketChannel.pipeline().addLast(new H2_粘包粘包问题重现客户端处理器());
        }
    };

}

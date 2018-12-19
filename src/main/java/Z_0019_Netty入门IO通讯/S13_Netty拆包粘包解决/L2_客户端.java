package Z_0019_Netty入门IO通讯.S13_Netty拆包粘包解决;

import Z_0019_Netty入门IO通讯.S12_Netty拆包粘包问题重现.H4_粘包粘包问题重现客户端处理器;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 要实现的功能是：客户端连接成功之后，向服务端写一段数据 ，服务端收到数据之后打印，并向客户端回一段数据
 */
public class L2_客户端 {

    public static void main(String[] args) {
        NioEventLoopGroup 数据读写子线程组 = new NioEventLoopGroup();
        Bootstrap 启动器 = new Bootstrap();

        // 还有 BIO 模型：OioSocketChannel
        Class<NioSocketChannel> NIO模型 = NioSocketChannel.class;
        ChannelInitializer<NioSocketChannel> 处理读写子线程逻辑 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                nioSocketChannel.pipeline().addLast(new H4_粘包粘包问题重现客户端处理器());
            }
        };

        启动器
                .group(数据读写子线程组)
                .channel(NIO模型)
                .handler(处理读写子线程逻辑);

        启动器.connect("127.0.0.1", 8000);

    }

}

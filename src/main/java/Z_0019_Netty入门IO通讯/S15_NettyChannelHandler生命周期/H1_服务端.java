package Z_0019_Netty入门IO通讯.S15_NettyChannelHandler生命周期;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器.C5_编码处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器.C5_解码处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器.C6_登录请求处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器.C7_拒绝非本协议以及定长解决拆包粘包处理器;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 要实现的功能是：客户端连接成功之后，向服务端写一段数据 ，服务端收到数据之后打印，并向客户端回一段数据
 */
public class H1_服务端 {

    public static void main(String[] args) {
        NioEventLoopGroup 监听新连接子线程组 = new NioEventLoopGroup();
        NioEventLoopGroup 数据读写子线程组 = new NioEventLoopGroup();

        // 还有 BIO 模型：OioServerSocketChannel
        Class<NioServerSocketChannel> NIO模型 = NioServerSocketChannel.class;
        ChannelInitializer<NioSocketChannel> 子线程处理读写线程 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                nioSocketChannel.pipeline().addLast(new C7_拒绝非本协议以及定长解决拆包粘包处理器());
                nioSocketChannel.pipeline().addLast(new H3_ChannelHandler生命周期打印());
                nioSocketChannel.pipeline().addLast(new C5_解码处理器());
                nioSocketChannel.pipeline().addLast(new C6_登录请求处理器());
                nioSocketChannel.pipeline().addLast(new C5_编码处理器());
            }
        };

        ServerBootstrap 启动器 = new ServerBootstrap();

        启动器
                .group(监听新连接子线程组, 数据读写子线程组)
                .channel(NIO模型)
                .childHandler(子线程处理读写线程);

        启动器.bind(8000).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[8000]绑定成功!");
            }else{
                System.out.println("端口绑定失败！");
            }
        });


    }

}

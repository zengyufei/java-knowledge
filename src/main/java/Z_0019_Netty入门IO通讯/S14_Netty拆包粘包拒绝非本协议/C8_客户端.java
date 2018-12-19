package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器.C5_编码处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器.C5_解码处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器.C6_消息发送响应处理器;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C4_处理器.C6_登录响应处理器;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 要实现的功能是：客户端连接成功之后，向服务端写一段数据 ，服务端收到数据之后打印，并向客户端回一段数据
 */
public class C8_客户端 {

    public static void main(String[] args) {
        NioEventLoopGroup 数据读写子线程组 = new NioEventLoopGroup();
        Bootstrap 启动器 = new Bootstrap();

        // 还有 BIO 模型：OioSocketChannel
        Class<NioSocketChannel> NIO模型 = NioSocketChannel.class;
        ChannelInitializer<NioSocketChannel> 处理读写子线程逻辑 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                nioSocketChannel.pipeline().addLast(new C5_解码处理器());
                nioSocketChannel.pipeline().addLast(new C6_登录响应处理器());
                nioSocketChannel.pipeline().addLast(new C6_消息发送响应处理器());
                nioSocketChannel.pipeline().addLast(new C5_编码处理器());
            }
        };

        启动器
                .group(数据读写子线程组)
                .channel(NIO模型)
                .handler(处理读写子线程逻辑);

        启动器.connect("127.0.0.1", 8000);

    }

}

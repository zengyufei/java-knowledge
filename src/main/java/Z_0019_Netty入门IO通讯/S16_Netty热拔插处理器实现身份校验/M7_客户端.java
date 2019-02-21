package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器.M4_K1_编码处理器;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器.M4_K2_解码处理器;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器.M4_K3_登录响应处理器;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器.M4_K4_发送消息响应处理器;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class M7_客户端 {

    public static void main(String[] args) {
        NioEventLoopGroup 读写事件处理线程组 = new NioEventLoopGroup();

        Class<NioSocketChannel> Nio线程模型实现类 = NioSocketChannel.class;
        ChannelInitializer<NioSocketChannel> 读写事件处理器集合 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                nioSocketChannel.pipeline().addLast(new M4_K2_解码处理器());
                nioSocketChannel.pipeline().addLast(new M4_K3_登录响应处理器());
                nioSocketChannel.pipeline().addLast(new M4_K4_发送消息响应处理器());
                nioSocketChannel.pipeline().addLast(new M4_K1_编码处理器());
            }
        };

        Bootstrap 启动器 = new Bootstrap();
        启动器
                .group(读写事件处理线程组)
                .channel(Nio线程模型实现类)
                .handler(读写事件处理器集合);

        启动器.connect("127.0.0.1", 8000);

    }

}

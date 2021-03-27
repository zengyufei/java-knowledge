package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_处理链.M12_编码处理器;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_处理链.M13_解码处理器;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_处理链.M15_登录响应处理器;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_处理链.M17_发送消息响应处理器;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class M8_客户端 {

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
                .handler(通道工厂)
                .connect("127.0.0.1", 8000);
    }

    private static final ChannelInitializer<NioSocketChannel> 通道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel nioSocketChannel) {
            nioSocketChannel.pipeline().addLast(new M13_解码处理器());
            nioSocketChannel.pipeline().addLast(new M15_登录响应处理器());
            nioSocketChannel.pipeline().addLast(new M17_发送消息响应处理器());
            nioSocketChannel.pipeline().addLast(new M12_编码处理器());
        }
    };

}

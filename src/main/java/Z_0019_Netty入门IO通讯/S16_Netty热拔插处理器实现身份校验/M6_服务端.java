package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器.*;
import cn.hutool.core.lang.Console;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class M6_服务端 {

    public static void main(String[] args) {
        NioEventLoopGroup 监听事件线程组 = new NioEventLoopGroup();
        NioEventLoopGroup 读写事件处理线程组 = new NioEventLoopGroup();

        Class<NioServerSocketChannel> Nio线程模型实现类 = NioServerSocketChannel.class;
        ChannelInitializer<NioSocketChannel> 读写事件处理器集合 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline().addLast(new M4_K2_解码处理器());
                nioSocketChannel.pipeline().addLast(new M4_K3_登录请求处理器());
                nioSocketChannel.pipeline().addLast(new M5_K5_登录状态验证处理器());
                nioSocketChannel.pipeline().addLast(new M4_K4_发送消息请求处理器());
                nioSocketChannel.pipeline().addLast(new M4_K1_编码处理器());
            }
        };

        ServerBootstrap 启动器 = new ServerBootstrap();

        启动器.group(监听事件线程组, 读写事件处理线程组)
                .channel(Nio线程模型实现类)
                .childHandler(读写事件处理器集合);

        启动器.bind(8000).addListener(future -> {
            if (future.isSuccess()) {
                Console.log("绑定[8000]端口成功!");
            } else {
                Console.log("绑定事变！");
            }
        });
    }

}

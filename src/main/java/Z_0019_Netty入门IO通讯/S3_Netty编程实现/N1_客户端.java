package Z_0019_Netty入门IO通讯.S3_Netty编程实现;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

public class N1_客户端 {

    public static void main(String[] args) throws Exception{
        Bootstrap 客户端启动器 = new Bootstrap();
        final NioEventLoopGroup 工作钩子 = new NioEventLoopGroup();

        final Class<NioSocketChannel> 工作模式 = NioSocketChannel.class;
        final StringEncoder 编码器 = new StringEncoder();

        客户端启动器
                .group(工作钩子)
                .channel(工作模式)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(编码器);
                    }
                });

        Channel 通道 = 客户端启动器.connect("127.0.0.1", 8000).channel();

        while (true) {
            通道.writeAndFlush(new Date() + ": hello world！");
            Thread.sleep(2000);
        }
    }

}

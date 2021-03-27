package Z_0019_Netty入门IO通讯.S3_Netty编程实现;

import Z_utils.输出;
import cn.hutool.core.date.DateUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.TimeUnit;


public class N2_客户端 {

    public static void main(String[] args) throws Exception {
        运行();
    }

    public static void 运行() {
        final NioEventLoopGroup 读写线程组 = new NioEventLoopGroup();
        final Class<NioSocketChannel> 套接字类型 = NioSocketChannel.class;
        final Bootstrap 客户端启动器 = new Bootstrap();
        final Channel 通道 = 客户端启动器
                //设置线程池
                .group(读写线程组)
                //设置socket工厂
                .channel(套接字类型)
                //设置管道工厂
                .handler(管道工厂)
                .connect("127.0.0.1", 8000)
                .channel();
        String 消息;
        while (true) {
            消息 = DateUtil.now() + ": hello world！";
            输出.客户端.控制台(消息);
            通道.writeAndFlush(消息);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<Channel> 管道工厂 = new ChannelInitializer<Channel>() {
        @Override
        protected void initChannel(Channel channel) {
            final StringEncoder 字符串字节码编码器 = new StringEncoder();
            channel.pipeline().addLast(字符串字节码编码器);
        }
    };
}

package Z_0019_Netty入门IO通讯.S3_Netty编程实现;

import cn.hutool.core.date.DateUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.TimeUnit;


public class N1_客户端 {
    
    public static void main(String[] args) throws Exception {
        
        final Bootstrap 客户端启动器 = new Bootstrap();
        final NioEventLoopGroup 读写线程组 = new NioEventLoopGroup();
        final Class<NioSocketChannel> 通道类型 = NioSocketChannel.class;
        final ChannelInitializer<Channel> 处理逻辑 = new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) {
                final StringEncoder 字符串字节码编码器 = new StringEncoder();
                channel.pipeline().addLast(字符串字节码编码器);
            }
        };
        
        客户端启动器
                .group(读写线程组)
                .channel(通道类型)
                .handler(处理逻辑);
        
        final Channel 通道 = 客户端启动器
                .connect("127.0.0.1", 8000)
                .channel();
        
        String 消息;
        while (true) {
            消息 = DateUtil.now() + ": hello world！";
            通道.writeAndFlush(消息);
            TimeUnit.SECONDS.sleep(2);
        }
    }
    
}

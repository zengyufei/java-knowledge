package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_接口层.Y3_抽象数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y3_实现层.Y5_登录请求数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y3_实现层.Y7_发送消息请求数据包;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y4_应用层.Y12_会话;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y5_工具类.Y13_会话工具类;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链.Y14_编码处理器;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链.Y15_解码处理器;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链.Y17_登录响应处理器;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y6_处理链.Y19_发送消息响应处理器;
import Z_utils.输出;
import cn.hutool.core.date.DateUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Y8_客户端 {

    public static void main(String[] args) throws Exception {
        运行("闪电侠");
    }

    public static void 运行(String 用户名) {
        final NioEventLoopGroup 读写线程组 = new NioEventLoopGroup();
        final Class<NioSocketChannel> 套接字类型 = NioSocketChannel.class;
        final Bootstrap 客户端启动器 = new Bootstrap();
        ChannelFuture connect = 客户端启动器
                //设置线程池
                .group(读写线程组)
                //设置socket工厂
                .channel(套接字类型)
                //设置管道工厂
                .handler(管道工厂)
                .connect("127.0.0.1", 8000);
        final Channel 通道 = connect.channel();

        while (!Y13_会话工具类.是否已经登录(通道)) {
            Y3_抽象数据包 y5_登录请求数据包 = new Y5_登录请求数据包("admin", "admin", 用户名);
            输出发送对方(通道, y5_登录请求数据包);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        while (Y13_会话工具类.是否已经登录(通道)) {
            Y12_会话 取出会话 = Y13_会话工具类.取出会话(通道);
            Scanner 控制台输入监听 = new Scanner(System.in);
            输出.客户端.控制台不换行("请输入需要发送的用户id: ");
            String 对方用户id = 控制台输入监听.next();
            输出.客户端.控制台不换行("请输入需要发送的消息内容: ");
            String 消息内容 = 控制台输入监听.next();
            输出.客户端.控制台("发送消息[{}]给{}", 消息内容, 对方用户id);
            Y3_抽象数据包 y7_发送消息请求数据包 = new Y7_发送消息请求数据包(对方用户id, 消息内容, DateUtil.now());
            输出发送对方(通道, y7_发送消息请求数据包);
        }
    }

    private static ChannelFuture 输出发送对方(Channel 通道, Y3_抽象数据包 y7_发送消息请求数据包) {
        return 通道.writeAndFlush(y7_发送消息请求数据包);
    }

    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<NioSocketChannel> 管道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel nioSocketChannel) {
            nioSocketChannel.pipeline().addLast(new Y15_解码处理器());
            nioSocketChannel.pipeline().addLast(new Y17_登录响应处理器());
            nioSocketChannel.pipeline().addLast(new Y19_发送消息响应处理器());
            nioSocketChannel.pipeline().addLast(new Y14_编码处理器());
        }
    };

}

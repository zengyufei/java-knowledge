package Z_0019_Netty入门IO通讯.S4_NettyAPI入门练习;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Map;

public class K1_服务端API {
    
    public static void main(String[] args) {
        /*
         * 单线程模型 (单Reactor单线程)
         * 多线程模型 (单Reactor多线程)
         * 主从多线程模型 (多Reactor多线程)
         * https://juejin.im/post/5dac6ef75188252bc1657ead
         * */
        final NioEventLoopGroup 主从多线程模型接收连接请求线程 = new NioEventLoopGroup();
        final NioEventLoopGroup 主从多线程模型处理线程 = new NioEventLoopGroup();
        // 还有 BIO 模型：OioServerSocketChannel
        final Class<NioServerSocketChannel> 通道类型 = NioServerSocketChannel.class;
        
        ChannelInitializer<NioServerSocketChannel> 连接线程处理逻辑 = new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel nioServerSocketChannel) {
                System.out.println("主线程启动了 Netty！");
                
                Attribute<Object> 给主线程的值 = nioServerSocketChannel.attr(AttributeKey.valueOf("给主线程设置值"));
                if (给主线程的值.get() != null) {
                    System.out.println("读取到给主线程的预设值： " + 给主线程的值.get());
                }
            }
        };
        ChannelInitializer<NioSocketChannel> 处理线程处理逻辑 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                System.out.println("子线程处理读写线程切换到本线程！");
                
                Attribute<Object> 给子线程的值 = nioSocketChannel.attr(AttributeKey.valueOf("给子线程设置值"));
                if (给子线程的值.get() != null) {
                    System.out.println("读取到给子线程的预设值： " + 给子线程的值.get());
                }
            }
        };
        
        GenericFutureListener<Future<? super Void>> 绑定端口监听器 = 端口绑定结果 -> {
            if (端口绑定结果.isSuccess()) {
                System.out.println("端口绑定成功！");
            } else {
                System.out.println("端口绑定失败！");
            }
        };
        
        //=========================================================================================================
        //=========================================================================================================
        //=========================================================================================================
        
        ServerBootstrap 启动器 = new ServerBootstrap();
        
        // 设置 监听新连接子线程组 心跳 true 启动 TCP 底层心跳
        启动器.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 设置 监听新连接子线程组 高实时性 false 开启，默认不要求高实时性
        启动器.childOption(ChannelOption.TCP_NODELAY, true);
        // 设置 主线程 三次握手后的连接临时存储最大长度，建立频繁时调大
        启动器.option(ChannelOption.SO_BACKLOG, 1024);
        
        启动器.attr(AttributeKey.valueOf("给主线程设置值"), "你好，主线程");
        启动器.childAttr(AttributeKey.valueOf("给子线程设置值"), "你好，child 子线程");
        
        启动器
                .group(主从多线程模型接收连接请求线程, 主从多线程模型处理线程)
                .channel(通道类型)
                .handler(连接线程处理逻辑)
                .childHandler(处理线程处理逻辑);
        
        启动器.bind(8000).addListener(绑定端口监听器);
        
        //=========================================================================================================
        //=========================================================================================================
        //=========================================================================================================
        
        
        Map<ChannelOption<?>, Object> 启动器的主线程的配置 = 启动器.config().options();
        Map<ChannelOption<?>, Object> 启动器的监听新连接子线程组的配置 = 启动器.config().childOptions();
        
        
        for (Map.Entry<ChannelOption<?>, Object> 启动器的主线程配置的配置项 : 启动器的主线程的配置.entrySet()) {
            ChannelOption<?> 启动器的主线程配置的配置项key = 启动器的主线程配置的配置项.getKey();
            Object 启动器的主线程配置的配置项值 = 启动器的主线程配置的配置项.getValue();
            System.out.println("启动器的主线程配置的配置项： " + 启动器的主线程配置的配置项key.name() + " == " + 启动器的主线程配置的配置项值.toString());
        }
        
        for (Map.Entry<ChannelOption<?>, Object> 启动器的监听新连接子线程组配置的配置项 : 启动器的监听新连接子线程组的配置.entrySet()) {
            ChannelOption<?> 启动器的监听新连接子线程组配置的配置项的key = 启动器的监听新连接子线程组配置的配置项.getKey();
            Object 启动器的监听新连接子线程组配置的配置项的值 = 启动器的监听新连接子线程组配置的配置项.getValue();
            System.out.println("启动器的监听新连接子线程组配置的配置项： " + 启动器的监听新连接子线程组配置的配置项的key.name() + " == " + 启动器的监听新连接子线程组配置的配置项的值.toString());
        }
        
    }
    
}

package S3_Netty编程实现;

import Z_utils.输出;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;


/**
 * 通道处理器集合ChannelPipeline可以处理或拦截关联通道的Inbound事件和Outbound操作。管道线实现了拦截过滤器模式，
 * 使开发者可以完全控制事件如何处理，以及通道处理器在管道中如何交互。
 * 创建管道
 * 每个通道拥有自己的管道，当通道创建时，管道自动创建
 * 管道事件流
 * 下图描述事件如何被管道中的通道处理器处理过程。一个IO事件被Inbound或Outbound通道处理器处理时，可以通过通道
 * 的上下文的相关事件传播方法，将事件转发给相邻的通道处理器，比如 ChannelHandlerContext#fireChannelRead(Object)和
 * ChannelHandlerContext#write(Object)方法。
 * <pre>
 *                                                 I/O Request
 *                                            via {@link Channel} or
 *                                        {@link ChannelHandlerContext}
 *                                                      |
 *  +---------------------------------------------------+---------------+
 *  |                           ChannelPipeline         |               |
 *  |                                                  \|/              |
 *  |    +---------------------+            +-----------+----------+    |
 *  |    | Inbound Handler  N  |            | Outbound Handler  1  |    |
 *  |    +----------+----------+            +-----------+----------+    |
 *  |              /|\                                  |               |
 *  |               |                                  \|/              |
 *  |    +----------+----------+            +-----------+----------+    |
 *  |    | Inbound Handler N-1 |            | Outbound Handler  2  |    |
 *  |    +----------+----------+            +-----------+----------+    |
 *  |              /|\                                  .               |
 *  |               .                                   .               |
 *  | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
 *  |        [ method call]                       [method call]         |
 *  |               .                                   .               |
 *  |               .                                  \|/              |
 *  |    +----------+----------+            +-----------+----------+    |
 *  |    | Inbound Handler  2  |            | Outbound Handler M-1 |    |
 *  |    +----------+----------+            +-----------+----------+    |
 *  |              /|\                                  |               |
 *  |               |                                  \|/              |
 *  |    +----------+----------+            +-----------+----------+    |
 *  |    | Inbound Handler  1  |            | Outbound Handler  M  |    |
 *  |    +----------+----------+            +-----------+----------+    |
 *  |              /|\                                  |               |
 *  +---------------+-----------------------------------+---------------+
 *                  |                                  \|/
 *  +---------------+-----------------------------------+---------------+
 *  |               |                                   |               |
 *  |       [ Socket.read() ]                    [ Socket.write() ]     |
 *  |                                                                   |
 *  |  Netty Internal I/O Threads (Transport Implementation)            |
 *  +-------------------------------------------------------------------+
 * </pre>
 * 在上图中左边，一个inbound事件，由下向上被Inbound通道处理器处理。一个Inbound通道处理器，一般处理来自IO线程的数据。
 * Inbound数据，通常通过实际的输入操作，如SocketChannel#read，从远端peer读取。如果inbound事件到达Inbound处理器的顶部，
 * 默认将会被抛弃，如果需要关注，可以log
 * <p>
 * 在上图中的右边， 一个Outbound事件，被Outbound通道处理器从上到下处理。一个Outbound通道处理器通常产生或者转发Outbound数据，
 * 不如写请求。如果outbound事件到达Outbound通道处理器的底部，那么将会被通道关联的Io线程处理。IO线程执行实际的输出操作，
 * 如SocketChannel#write。
 * <p>
 * 来看一个例子，假设创建管道如下
 * <pre>
 * {@link ChannelPipeline} p = ...;
 * p.addLast("1", new InboundHandlerA());
 * p.addLast("2", new InboundHandlerB());
 * p.addLast("3", new OutboundHandlerA());
 * p.addLast("4", new OutboundHandlerB());
 * p.addLast("5", new InboundOutboundHandlerX());
 * </pre>
 * 在上述示例中，Inbound开头的为Inbound处理器，Outbound开头的为Outbound处理器
 * <p>
 * inbound事件处理的顺序为1, 2, 3, 4, 5，outbound事件为5, 4, 3, 2, 1。基于管道的top原则将会跳过一些无用的处理器，
 * 以缩短通道处理器栈的深度。
 * [list]
 * 由于3,4没有实现inbound通道处理器，因此实际inbound通道处理器的顺序为1,2,5.
 * 由于1,2没有实现inbound通道处理器，因此实际inbound通道处理器的顺序为5, 4, 3.
 * 如果5实现了inbound和Outbound，则inbound事件，处理器顺序为125，oubound事件为543.
 * [/list]
 */
public class N1_服务端 {

    /*
     * 单线程模型 (单Reactor单线程)
     * 多线程模型 (单Reactor多线程)
     * 主从多线程模型 (多Reactor多线程)
     * https://juejin.im/post/5dac6ef75188252bc1657ead
     */

    public static void main(String[] args) {
        运行();
    }

    public static void 运行() {
        final NioEventLoopGroup boss线程组 = new NioEventLoopGroup();
        final NioEventLoopGroup work线程组 = new NioEventLoopGroup();
        final Class<NioServerSocketChannel> 套接字类型 = NioServerSocketChannel.class;
        final ServerBootstrap 服务端启动器 = new ServerBootstrap();
        服务端启动器
                //设置线程池 前者用来处理accept事件，后者用于处理已经建立的连接的io
                .group(boss线程组, work线程组)
                //设置socket工厂
                .channel(套接字类型)
                //设置管道工厂
                .childHandler(管道工厂)
                //绑定端口8000
                .bind(8000);
    }

    // 是一种特殊的ChannelInboundHandler
    private static final ChannelInitializer<NioSocketChannel> 管道工厂 = new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel 通道) {
            final StringDecoder 字节码转字符解码器 = new StringDecoder();
            // 从上到下
            通道.pipeline().addLast(字节码转字符解码器);
            通道.pipeline().addLast(消息处理器);
        }
    };

    // 网络数据从外部流入内部
    private static final SimpleChannelInboundHandler<String> 消息处理器 = new SimpleChannelInboundHandler<String>() {
        @Override
        protected void channelRead0(ChannelHandlerContext 上下文, String 消息) {
            输出.服务端.控制台输出(消息);
        }
    };
}

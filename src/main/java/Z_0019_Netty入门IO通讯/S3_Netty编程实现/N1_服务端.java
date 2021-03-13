package Z_0019_Netty入门IO通讯.S3_Netty编程实现;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
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
 *
 * 转发事件到下一个处理器
 * 在上图中，你可能已经注意到，一个处理器不得不调用关联的上下文的事件传播方法，将事件传播给下一个处理器。
 * 这些方法如下：
 * [list]
 * <li>Inbound event propagation methods:
 * [list]
 * [*]{@link ChannelHandlerContext#fireChannelRegistered()}
 *
 * [*]{@link ChannelHandlerContext#fireChannelActive()}
 *
 * [*]{@link ChannelHandlerContext#fireChannelRead(Object)}
 *
 * [*]{@link ChannelHandlerContext#fireChannelReadComplete()}
 *
 * [*]{@link ChannelHandlerContext#fireExceptionCaught(Throwable)}
 *
 * [*]{@link ChannelHandlerContext#fireUserEventTriggered(Object)}
 *
 * [*]{@link ChannelHandlerContext#fireChannelWritabilityChanged()}
 *
 * [*]{@link ChannelHandlerContext#fireChannelInactive()}
 *
 * [*]{@link ChannelHandlerContext#fireChannelUnregistered()}
 *
 * [/list]
 * </li>
 * <li>Outbound event propagation methods:
 * [list]
 * [*]{@link ChannelHandlerContext#bind(SocketAddress, ChannelPromise)}
 *
 * [*]{@link ChannelHandlerContext#connect(SocketAddress, SocketAddress, ChannelPromise)}
 *
 * [*]{@link ChannelHandlerContext#write(Object, ChannelPromise)}
 *
 * [*]{@link ChannelHandlerContext#flush()}
 *
 * [*]{@link ChannelHandlerContext#read()}
 *
 * [*]{@link ChannelHandlerContext#disconnect(ChannelPromise)}
 *
 * [*]{@link ChannelHandlerContext#close(ChannelPromise)}
 *
 * [*]{@link ChannelHandlerContext#deregister(ChannelPromise)}
 *
 * [/list]
 * </li>
 * [/list]
 *
 * 下面的实例展示事件如何传播
 * <pre>
 * public class MyInboundHandler extends {@link ChannelInboundHandlerAdapter} {
 *     {@code @Override}
 *     public void channelActive({@link ChannelHandlerContext} ctx) {
 *         System.out.println("Connected!");
 *         ctx.fireChannelActive();
 *     }
 * }
 *
 * public class MyOutboundHandler extends {@link ChannelOutboundHandlerAdapter} {
 *     {@code @Override}
 *     public void close({@link ChannelHandlerContext} ctx, {@link ChannelPromise} promise) {
 *         System.out.println("Closing ..");
 *         ctx.close(promise);
 *     }
 * }
 * </pre>
 *
 * <p>构建管道
 * 用户可能在管道中有多个通道处理器，处理IO事件和IO请求操作(write and close)。比如，一个典型的服务器，在每个通道的
 * 管道中有如下handler，处理过程可能因为不同的协议和业务逻辑而不同
 *
 * [list=1]
 * [*]Protocol Decoder - translates binary data (e.g. {@link ByteBuf}) into a Java object.
 *
 * [*]Protocol Encoder - translates a Java object into binary data.
 *
 * [*]Business Logic Handler - performs the actual business logic (e.g. database access).
 *
 * 解码器，编码器，业务逻辑Handler
 * [/list]
 *
 * 下面为一个实例
 * <pre>IO事件操作执行器组
 * static final {@link EventExecutorGroup} group = new {@link DefaultEventExecutorGroup}(16);
 * ...
 * 获取通道的管道
 * {@link ChannelPipeline} pipeline = ch.pipeline();
 * 添加解码器和编码器
 * pipeline.addLast("decoder", new MyProtocolDecoder());
 * pipeline.addLast("encoder", new MyProtocolEncoder());
 *
 * 告诉管道，在不同于IO线程的事件执行器组中，执行通道处理器的事件执行方法，以保证IO线程不会被
 * 一个耗时任务阻塞。如果你的业务逻辑完全异步或能够快速的完成，你不要添加一个事件执行器组。
 * pipeline.addLast(group, "handler", new MyBusinessLogicHandler());
 * </pre>
 *
 * <p>线程安全
 * 由于管道时线程安全的，通道处理器可以在任何时候，添加或移除。比如：当有一些敏感数据要交换时，插入加密Handler，
 * 在交换后，移除。
 */
public class N1_服务端 {
    
    public static void main(String[] args) {
        /*
         * 单线程模型 (单Reactor单线程)
         * 多线程模型 (单Reactor多线程)
         * 主从多线程模型 (多Reactor多线程)
         * https://juejin.im/post/5dac6ef75188252bc1657ead
         * */
        final NioEventLoopGroup 主从多线程模型接收连接请求线程 = new NioEventLoopGroup();
        final NioEventLoopGroup 主从多线程模型处理线程 = new NioEventLoopGroup();
        
        // 异步的服务器端 TCP Socket 连接
        final Class<NioServerSocketChannel> 通道类型 = NioServerSocketChannel.class;
    
        final ChannelInitializer<NioSocketChannel> 处理逻辑 = new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel 通道) {
                final StringDecoder 字节码转字符解码器 = new StringDecoder();
                final SimpleChannelInboundHandler<String> 消息处理器 = new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext 上下文, String 消息) {
                        System.out.println(消息);
                    }
                };
                通道.pipeline().addLast(字节码转字符解码器);
                通道.pipeline().addLast(消息处理器);
            }
        };
    
        final ServerBootstrap 服务端启动器 = new ServerBootstrap();
        服务端启动器
                .group(主从多线程模型接收连接请求线程, 主从多线程模型处理线程)
                .channel(通道类型)
                .childHandler(处理逻辑)
                .bind(8000);
    }
    
}

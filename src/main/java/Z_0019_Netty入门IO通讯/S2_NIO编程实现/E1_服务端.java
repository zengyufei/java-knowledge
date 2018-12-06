package Z_0019_Netty入门IO通讯.S2_NIO编程实现;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class E1_服务端 {

    public static void main(String[] args) throws Exception {
        Selector 监听钩子 = Selector.open(); // 用来处理服务端连接请求
        Selector 读钩子 = Selector.open(); // 用来处理客户端发过来的信息

        new Thread(() -> {
            try {
                // 1: 启动设置
                ServerSocketChannel 服务端通道 = ServerSocketChannel.open();
                服务端通道.socket().bind(new InetSocketAddress(8000));
                服务端通道.configureBlocking(false);
                // 2: 注册为 监听钩子，到时候服务端所有请求都会丢给钩子，我们的操作直接使用钩子即可
                服务端通道.register(监听钩子, SelectionKey.OP_ACCEPT);

                while (true) {
                    // 利用 监听钩子 监听，这里的 1 指的是阻塞的时间为 1ms
                    if (监听钩子.select(1)>0) {
                        Set<SelectionKey> 钩子集合 = 监听钩子.selectedKeys();
                        Iterator<SelectionKey> 钩子集合遍历子 = 钩子集合.iterator();
                        while (钩子集合遍历子.hasNext()) {
                            SelectionKey 钩子 = 钩子集合遍历子.next();
                            // 3: 如果是新连接
                            if (钩子.isAcceptable()) {
                               try {
                                   ServerSocketChannel 通道 = (ServerSocketChannel) 钩子.channel();
                                   SocketChannel 客户端请求 = 通道.accept();
                                   客户端请求.configureBlocking(false);
                                   // 4: 注册为 读钩子
                                   客户端请求.register(读钩子, SelectionKey.OP_READ);
                               }finally {
                                   钩子集合遍历子.remove();
                               }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                while (true) {
                    // 5: 轮询 读钩子
                    if (读钩子.select(1) > 0) {
                        Set<SelectionKey> 钩子集合 = 读钩子.selectedKeys();
                        Iterator<SelectionKey> 钩子集合遍历子 = 钩子集合.iterator();
                        while (钩子集合遍历子.hasNext()) {
                            SelectionKey 钩子 = 钩子集合遍历子.next();
                            if (钩子.isReadable()) {
                                try {
                                    SocketChannel 通道 = (SocketChannel) 钩子.channel();
                                    ByteBuffer 可写盒子 = ByteBuffer.allocate(1024);
                                    // 从通道读数据，写入 可写盒子
                                    通道.read(可写盒子);
                                    // 将盒子反转为 可读盒子
                                    可写盒子.flip();
                                    // 反转后重新赋值
                                    ByteBuffer 可读盒子 = 可写盒子;
                                    System.out.println(new String(可读盒子.array()).trim());
                                }finally {
                                    钩子集合遍历子.remove();
                                    // 重新注册为 读钩子
                                    钩子.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}

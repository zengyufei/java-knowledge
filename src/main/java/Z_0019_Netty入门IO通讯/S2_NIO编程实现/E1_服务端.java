package Z_0019_Netty入门IO通讯.S2_NIO编程实现;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
        /*
         * 伪代码:
         *      开启选择器.
         *      开启服务端通道.
         *      通道绑定端口.
         *      通道设置非阻塞模式.
         *      通道注册选择器, 监听客户端接入.
         *      从选择器中取出就绪的选择器.
         *          如果是新连接, 则转为设置选择器为读取.
         *          如果是读取, 则使用容器读取信息,转为字符串输出.
         */
        Selector 选择器 = Selector.open(); // 用来处理服务端连接请求
        Selector 读选择器 = Selector.open(); // 用来处理客户端发过来的信息
    
        ThreadUtil.execute(() -> {
            String 客户端ip = null;
            System.out.println("启动服务器, 等待客户端接入.......");
            try {
                // 1: 启动设置
                ServerSocketChannel 服务端通道 = ServerSocketChannel.open();
                InetSocketAddress 端口设置 = new InetSocketAddress(8000);
                服务端通道.socket().bind(端口设置);
                服务端通道.configureBlocking(false);
                // 2: 注册为 监听钩子，到时候服务端所有请求都会丢给钩子，我们的操作直接使用钩子即可
                服务端通道.register(选择器, SelectionKey.OP_ACCEPT);
                
                while (true) {
                    // 利用 监听钩子 监听，这里的 1 指的是阻塞的时间为 1ms
                    int timeout = 1000;
                    if (选择器.select(timeout) > 0) {
                        Set<SelectionKey> 获取连接的唯一标识 = 选择器.selectedKeys();
                        Iterator<SelectionKey> 连接的唯一标识迭代器 = 获取连接的唯一标识.iterator();
                        while (连接的唯一标识迭代器.hasNext()) {
                            SelectionKey 连接的唯一标识 = 连接的唯一标识迭代器.next();
                            // 3: 如果是新连接
                            if (连接的唯一标识.isAcceptable()) {
                                try {
                                    ServerSocketChannel 通道 = (ServerSocketChannel) 连接的唯一标识.channel();
                                    客户端ip = 通道.getLocalAddress().toString();
                                    System.out.println("客户端" + 客户端ip + "接入.");
                                    SocketChannel 客户端请求 = 通道.accept();
                                    客户端请求.configureBlocking(false);
                                    // 4: 注册为 读选择器
                                    客户端请求.register(选择器, SelectionKey.OP_READ);
                                } finally {
                                    连接的唯一标识迭代器.remove();
                                }
                            } else if (连接的唯一标识.isReadable()) {
                                try {
                                    SocketChannel 通道 = (SocketChannel) 连接的唯一标识.channel();
                                    ByteBuffer 承载数据的容器 = ByteBuffer.allocate(1024);
                                    // 从通道读数据，写入 承载数据的容器
                                    通道.read(承载数据的容器);
                                    // 将盒子反转为 可读盒子
                                    承载数据的容器.flip();
                                    // 反转后重新赋值
                                    String 从客户端发送过来的消息 = new String(承载数据的容器.array()).trim();
                                    System.out.println("从客户端发送过来的消息: " + 从客户端发送过来的消息);
                                } finally {
                                    // 重新注册为 读选择器
                                    连接的唯一标识.interestOps(SelectionKey.OP_READ);
                                    连接的唯一标识迭代器.remove();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                if (StrUtil.containsAnyIgnoreCase(ExceptionUtil.getMessage(e), "远程主机强迫关闭了一个现有的连接")) {
                    System.out.println("客户端[" + 客户端ip + "]断开连接.");
                } else {
                    throw new RuntimeException(e);
                }
            }
        });
        
        
        /*new Thread(() -> {
            try {
                while (true) {
                    // 5: 轮询 读选择器
                    if (读选择器.select(1) > 0) {
                        Set<SelectionKey> 钩子集合 = 读选择器.selectedKeys();
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
                                } finally {
                                    钩子集合遍历子.remove();
                                    // 重新注册为 读选择器
                                    钩子.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();*/
    }
    
}

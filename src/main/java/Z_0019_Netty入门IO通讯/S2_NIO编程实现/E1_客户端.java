package Z_0019_Netty入门IO通讯.S2_NIO编程实现;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class E1_客户端 {
    
    public static void main(String[] args) throws Exception {
        /*
         * 伪代码:
         *       创建服务端地址.
         *       准备好承载数据的容器.
         *       准备好连接线程池和发送接收线程池.
         *       打开连接通道.
         *           设置非阻塞连接.
         *           进行连接.
         *           并设置连接线程池的作用.
         *       从连接线程池中取出所有连接.
         *       如果服务端有发送消息过来,就能取出连接.
         *
         *
         * */
        InetSocketAddress 服务端地址 = new InetSocketAddress("127.0.0.1", 8000);
        ByteBuffer 承载数据的容器 = ByteBuffer.allocate(1024);
        // 创建两个线程池, Selector 其实就是线程池, 在下面设置这两个线程池的作用
        Selector 连接选择器 = Selector.open();
        Selector 发送接收选择器 = Selector.open();
        
        ThreadUtil.execute(() -> {
            System.out.println("启动客户端.");
            try {
                SocketChannel 客户端通道 = SocketChannel.open();
                // 如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
                // 这行代码必须在 connect 之前
                客户端通道.configureBlocking(false);
                客户端通道.connect(服务端地址);
                // 将通道注册到连接选择器上面
                客户端通道.register(连接选择器, SelectionKey.OP_CONNECT);
                
                while (true) {
                    int 超时设置 = 1000;
                    int 从连接线程池中取出一条连接 = 连接选择器.select(超时设置);
                    if (从连接线程池中取出一条连接 > 0) {
                        Set<SelectionKey> 获取连接的唯一标识 = 连接选择器.selectedKeys();
                        Iterator<SelectionKey> 连接的唯一标识迭代器 = 获取连接的唯一标识.iterator();
                        
                        while (连接的唯一标识迭代器.hasNext()) {
                            SelectionKey 连接的唯一标识 = 连接的唯一标识迭代器.next();
                            
                            // 是否 “连接就绪”
                            if (连接的唯一标识.isConnectable()) {
                                SocketChannel 通道 = (SocketChannel) 连接的唯一标识.channel();
                                if (通道.finishConnect()) {
                                    System.out.println("与服务端建立连接成功");
                                } else {
                                    System.out.println("与服务端建立连接失败");
                                }
                                
                                // 给线程池设它的作用, 用来发送数据
                                连接的唯一标识.interestOps(SelectionKey.OP_WRITE);
                                // 通道.register(连接选择器, SelectionKey.OP_WRITE);
                                // 通道.register(发送接收选择器, SelectionKey.OP_WRITE);
                            } else if (连接的唯一标识.isWritable()) { // 是否 “写就绪”
                                TimeUnit.SECONDS.sleep(1);
                                SocketChannel 通道 = (SocketChannel) 连接的唯一标识.channel();
                                承载数据的容器.clear();
                                String msg = new Date() + ": hello world！";
                                承载数据的容器.put(msg.getBytes());
                                承载数据的容器.flip();
                                通道.write(承载数据的容器);
                                System.out.println("客户端发送消息: " + msg);
                                
                                // 给线程池设它的作用, 用来发送数据
                                连接的唯一标识.interestOps(SelectionKey.OP_WRITE);
                                // 通道.register(连接选择器, SelectionKey.OP_WRITE);
                                // 通道.register(发送接收选择器, SelectionKey.OP_WRITE);
                            }
                            连接的唯一标识迭代器.remove();
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                String message = ExceptionUtil.getMessage(e);
                if (StrUtil.containsAnyIgnoreCase(message, "远程主机强迫关闭了一个现有的连接")) {
                    System.out.println("服务端停止服务, 本客户端也停止.");
                    System.exit(0);
                } else {
                    throw new RuntimeException(e);
                }
            }
        });

/*
        new Thread(() -> {
            try {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        if (发送接收选择器.select(1) > 0) {
                            Set<SelectionKey> 钩子集合 = 发送接收选择器.selectedKeys();
                            Iterator<SelectionKey> 钩子集合遍历子 = 钩子集合.iterator();
                            while (钩子集合遍历子.hasNext()) {
                                SelectionKey 钩子 = 钩子集合遍历子.next();

                                if (钩子.isWritable()) {
                                    try {
                                        SocketChannel 通道 = (SocketChannel) 钩子.channel();
                                        承载数据的容器.clear();
                                        承载数据的容器.put((new Date() + ": hello world！").getBytes());
                                        承载数据的容器.flip();
                                        通道.write(承载数据的容器);
                                    } finally {
                                        钩子集合遍历子.remove();
                                        钩子.interestOps(SelectionKey.OP_WRITE);
                                    }
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();*/
    }
    
}

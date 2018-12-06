package Z_0019_Netty入门IO通讯.S2_NIO编程实现;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class E1_客户端 {

    public static void main(String[] args) throws Exception {
        ByteBuffer 可写盒子 = ByteBuffer.allocate(1024);
        Selector 连接钩子 = Selector.open();
        Selector 发送钩子 = Selector.open();

        new Thread(() -> {
            try {
                SocketChannel 客户端通道 = SocketChannel.open();
                客户端通道.configureBlocking(false); // 这行代码必须在 connect 之前
                客户端通道.connect(new InetSocketAddress("127.0.0.1", 8000));
                客户端通道.register(连接钩子, SelectionKey.OP_CONNECT);

                while (true) {
                    if (连接钩子.select(1) > 0) {
                        Set<SelectionKey> 钩子集合 = 连接钩子.selectedKeys();
                        Iterator<SelectionKey> 钩子集合遍历子 = 钩子集合.iterator();

                        while (钩子集合遍历子.hasNext()) {
                            SelectionKey 钩子 = 钩子集合遍历子.next();

                            if (钩子.isConnectable()) {
                                SocketChannel 通道 = (SocketChannel) 钩子.channel();
                                通道.finishConnect();
                                通道.register(发送钩子, SelectionKey.OP_WRITE);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        if (发送钩子.select(1) > 0) {
                            Set<SelectionKey> 钩子集合 = 发送钩子.selectedKeys();
                            Iterator<SelectionKey> 钩子集合遍历子 = 钩子集合.iterator();
                            while (钩子集合遍历子.hasNext()) {
                                SelectionKey 钩子 = 钩子集合遍历子.next();

                                if (钩子.isWritable()) {
                                    try {
                                        SocketChannel 通道 = (SocketChannel) 钩子.channel();
                                        可写盒子.clear();
                                        可写盒子.put((new Date() + ": hello world！").getBytes());
                                        可写盒子.flip();
                                        通道.write(可写盒子);
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
        }).start();
    }

}

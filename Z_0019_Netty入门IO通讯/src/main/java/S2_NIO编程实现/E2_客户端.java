package S2_NIO编程实现;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
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
public class E2_客户端 {

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
    public static void main(String[] args) throws Exception {
        运行();
    }

    public static void 运行() throws IOException {
        // 创建两个线程池, Selector 其实就是线程池, 在下面设置这两个线程池的作用
        Selector 连接选择器 = Selector.open();
        Selector 写选择器 = Selector.open();
        启动客户端(连接选择器);
        接待服务(连接选择器, 写选择器);
        读取消息服务(写选择器);
    }

    private static void 接待服务(Selector 连接选择器, Selector 写选择器) {
        ThreadUtil.execute(() -> {
            System.out.println("启动客户端.");
            try {
                while (true) {
                    int 超时设置 = 1000;
                    int 从连接线程池中取出一条连接 = 连接选择器.select(超时设置);
                    if (从连接线程池中取出一条连接 > 0) {
                        Set<SelectionKey> 获取连接选择器钩子集合 = 连接选择器.selectedKeys();
                        Iterator<SelectionKey> 获取连接选择器钩子迭代器 = 获取连接选择器钩子集合.iterator();

                        while (获取连接选择器钩子迭代器.hasNext()) {
                            SelectionKey 连接选择器钩子 = 获取连接选择器钩子迭代器.next();
                            // 是否 “连接就绪”
                            if (连接选择器钩子.isConnectable()) {
                                SocketChannel 通道 = (SocketChannel) 连接选择器钩子.channel();
                                if (通道.finishConnect()) {
                                    System.out.println("与服务端建立连接成功");
                                } else {
                                    System.out.println("与服务端建立连接失败");
                                }
                                // 设置它的作用, 用来发送数据
                                注册写消息线程服务(写选择器, 通道);
                            }
                            // 通道.register(连接选择器, SelectionKey.OP_WRITE);
                            // 如果注册的是 连接选择器 则打开下面代码
                            /*
                            else if (连接选择器钩子.isWritable()) { // 是否 “写就绪”
                                发送消息给服务器(承载数据的容器, 连接选择器钩子);
                            }*/
                            获取连接选择器钩子迭代器.remove();
                        }
                    }
                }
            } catch (Exception e) {
                输出异常(e);
            }
        });
    }

    private static void 注册写消息线程服务(Selector 写选择器, SocketChannel 通道) throws ClosedChannelException {
        注册写消息线程服务(写选择器, 通道, SelectionKey.OP_WRITE);
    }

    private static void 注册写消息线程服务(Selector 写选择器, SocketChannel 通道, int opWrite) throws ClosedChannelException {
        通道.register(写选择器, opWrite);
    }

    private static void 读取消息服务(Selector 写选择器) {
        ThreadUtil.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    if (写选择器.select(1) > 0) {
                        Set<SelectionKey> 获取写选择器钩子集合 = 写选择器.selectedKeys();
                        Iterator<SelectionKey> 获取写选择器钩子迭代器 = 获取写选择器钩子集合.iterator();
                        while (获取写选择器钩子迭代器.hasNext()) {
                            SelectionKey 写选择器钩子 = 获取写选择器钩子迭代器.next();
                            if (写选择器钩子.isWritable()) {
                                发送消息给服务器(获取写选择器钩子迭代器, 写选择器钩子);
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static void 发送消息给服务器(Iterator<SelectionKey> 写选择器钩子迭代器, SelectionKey 选择器钩子) throws InterruptedException, IOException {
        ByteBuffer 承载数据的容器 = ByteBuffer.allocate(1024);
        try {
            TimeUnit.SECONDS.sleep(1);
            SocketChannel 通道 = (SocketChannel) 选择器钩子.channel();
            承载数据的容器.clear();
            String msg = new Date() + ": hello world！";
            承载数据的容器.put(msg.getBytes());
            承载数据的容器.flip();
            通道.write(承载数据的容器);
        } finally {
            写选择器钩子迭代器.remove();
            // 给线程池设它的作用, 用来发送数据
            选择器钩子.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private static void 启动客户端(Selector 连接选择器) throws IOException {
        SocketChannel 客户端通道 = SocketChannel.open();
        InetSocketAddress 服务端地址 = new InetSocketAddress("127.0.0.1", 8000);
        // 如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
        // 这行代码必须在 connect 之前
        客户端通道.configureBlocking(false);
        客户端通道.connect(服务端地址);
        // 将通道注册到连接选择器上面
        客户端通道.register(连接选择器, SelectionKey.OP_CONNECT);
    }

    private static void 输出异常(Exception e) {
        String message = ExceptionUtil.getMessage(e);
        if (StrUtil.containsAnyIgnoreCase(message, "远程主机强迫关闭了一个现有的连接")) {
            System.out.println("服务端停止服务, 本客户端也停止.");
            System.exit(0);
        } else {
            throw new RuntimeException(e);
        }
    }

}

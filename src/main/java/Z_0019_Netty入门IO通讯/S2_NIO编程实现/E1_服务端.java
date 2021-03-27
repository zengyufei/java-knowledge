package Z_0019_Netty入门IO通讯.S2_NIO编程实现;

import Z_utils.输出;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class E1_服务端 {

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
    public static void main(String[] args) throws Exception {
        运行();
    }

    public static void 运行() throws IOException {
        Selector 选择器 = Selector.open(); // 用来处理服务端连接请求
        Selector 读选择器 = Selector.open(); // 用来处理客户端发过来的信息
        ServerSocketChannel 服务端通道 = 服务端启动();
        接待服务(服务端通道, 选择器, 读选择器);
        读取消息服务(读选择器);
    }

    private static ServerSocketChannel 服务端启动() throws IOException {
        // 1: 启动设置
        ServerSocketChannel 服务端通道 = ServerSocketChannel.open();
        InetSocketAddress 端口设置 = new InetSocketAddress(8000);
        服务端通道.socket().bind(端口设置);
        服务端通道.configureBlocking(false);
        return 服务端通道;
    }

    private static void 接待服务(ServerSocketChannel 服务端通道, Selector 选择器, Selector 读选择器) {
        ThreadUtil.execute(() -> {
            String 客户端ip = null;
            输出.服务端.控制台("启动服务器, 等待客户端接入.......");
            try {
                注册服务端接收请求钩子(选择器, 服务端通道);
                while (true) {
                    // 利用 监听钩子 监听，这里的 1 指的是阻塞的时间为 1ms
                    int timeout = 1000;
                    if (选择器.select(timeout) > 0) {
                        Set<SelectionKey> 获取选择器钩子集合 = 选择器.selectedKeys();
                        Iterator<SelectionKey> 选择器钩子迭代器 = 获取选择器钩子集合.iterator();
                        while (选择器钩子迭代器.hasNext()) {
                            SelectionKey 选择器钩子 = 选择器钩子迭代器.next();
                            // 3: 如果是新连接
                            boolean 选择器钩子是否连接状态 = 选择器钩子.isAcceptable();
                            //boolean 选择器钩子是否可读状态 = 选择器钩子.isReadable();
                            if (选择器钩子是否连接状态) {
                                SocketChannel 客户端请求 = 处理客户端请求并返回(选择器钩子迭代器, 选择器钩子);
                                注册读取客户端消息钩子(读选择器, 客户端请求);
                            }
                            // 选择器 注册为 SelectionKey.OP_READ 时使用
                            /*else if (选择器钩子是否可读状态) {
                                读取消息(选择器钩子迭代器, 选择器钩子);
                            }*/
                        }
                    }
                }
            } catch (Exception e) {
                输出异常(客户端ip, e);
            }
        });
    }

    private static void 读取消息服务(Selector 读选择器) {
        // 读选择器 注册为 SelectionKey.OP_READ 时使用
        ThreadUtil.execute(() -> {
            try {
                while (true) {
                    // 5: 轮询 读选择器
                    if (读选择器.select(1) > 0) {
                        Set<SelectionKey> 获取读选择器钩子集合 = 读选择器.selectedKeys();
                        Iterator<SelectionKey> 读选择器钩子迭代器 = 获取读选择器钩子集合.iterator();
                        while (读选择器钩子迭代器.hasNext()) {
                            SelectionKey 读选择器钩子 = 读选择器钩子迭代器.next();
                            boolean 选择器钩子是否可读状态 = 读选择器钩子.isReadable();
                            if (选择器钩子是否可读状态) {
                                读取消息(读选择器钩子迭代器, 读选择器钩子);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    private static void 注册服务端接收请求钩子(Selector 选择器, ServerSocketChannel 服务端通道) throws ClosedChannelException {
        // 2: 注册为 监听钩子，到时候服务端所有请求都会丢给钩子，我们的操作直接使用钩子即可
        服务端通道.register(选择器, SelectionKey.OP_ACCEPT);
    }

    private static void 注册读取客户端消息钩子(Selector 读选择器, SocketChannel 客户端请求) throws ClosedChannelException {
        // 4: 注册为 读  , 可以使用 选择器/读选择器
        客户端请求.register(读选择器, SelectionKey.OP_READ);
    }

    private static SocketChannel 处理客户端请求并返回(Iterator<SelectionKey> 选择器钩子迭代器, SelectionKey 选择器钩子) throws IOException {
        SocketChannel 客户端请求;
        try {
            ServerSocketChannel 客户端通道 = (ServerSocketChannel) 选择器钩子.channel();
            客户端请求 = 客户端通道.accept();
            打印客户端ip(客户端请求);
            客户端请求.configureBlocking(false);
        } finally {
            选择器钩子迭代器.remove();
        }
        return 客户端请求;
    }

    private static String 打印客户端ip(SocketChannel 客户端请求) throws IOException {
        String 客户端ip = 客户端请求.getLocalAddress().toString();
        输出.服务端.控制台("客户端{}接入.", 客户端ip);
        return 客户端ip;
    }

    private static void 读取消息(Iterator<SelectionKey> 读选择器钩子迭代器, SelectionKey 读选择器钩子) throws IOException {
        try {
            SocketChannel 通道 = (SocketChannel) 读选择器钩子.channel();
            ByteBuffer 可写盒子 = ByteBuffer.allocate(1024);
            // 从通道读数据，写入 可写盒子
            通道.read(可写盒子);
            // 将盒子反转为 可读盒子
            可写盒子.flip();
            // 反转后重新赋值
            ByteBuffer 可读盒子 = 可写盒子;
            输出.服务端.控制台("从客户端发送过来的消息: {}", new String(可读盒子.array()).trim());
        } finally {
            读选择器钩子迭代器.remove();
            // 重新注册为 读选择器
            读选择器钩子.interestOps(SelectionKey.OP_READ);
        }
    }

    private static void 输出异常(String 客户端ip, Exception e) {
        if (StrUtil.containsAnyIgnoreCase(ExceptionUtil.getMessage(e), "远程主机强迫关闭了一个现有的连接")) {
            输出.服务端.控制台("客户端[{}]断开连接.", 客户端ip);
        } else {
            throw new RuntimeException(e);
        }
    }

}

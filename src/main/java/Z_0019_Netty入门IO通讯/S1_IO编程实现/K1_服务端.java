package Z_0019_Netty入门IO通讯.S1_IO编程实现;

import Z_utils.服务端输出;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class K1_服务端 {

    /*
     * 伪代码:
     *      选定端口.
     *      创建该端口socker服务端程序.
     *      程序阻塞获取客户端连接.
     *      打开客户端连接输入通道(网络输入到内存).
     *      线程读取数据.
     *      使用字节流读取数据.
     *      将字节流转码成字符串.
     */
    public static void main(String[] args) throws Exception {
        运行();
    }

    public static void 运行() throws IOException {
        /*
         * 在传统的 IO 模型中，每个连接创建成功之后都需要一个线程来维护。
         * 每个线程包含一个 while 死循环，那么 1w 个连接对应 1w 个线程，继而 1w 个 while 死循环，这就带来如下几个问题：
         * 线程资源受限：线程是操作系统中非常宝贵的资源，同一时刻有大量的线程处于阻塞状态是非常严重的资源浪费，操作系统耗不起
         * 线程切换效率低下：单机 CPU 核数固定，线程爆炸之后操作系统频繁进行线程切换，应用性能急剧下降。
         * 除了以上两个问题，IO 编程中，我们看到数据读写是以字节流为单位。
         */
        ServerSocket 服务端套接字 = new ServerSocket(8000);
        // 1: 启动
        ThreadUtil.execute(() -> {
            服务端输出.控制台("启动");
            服务端输出.控制台("阻塞, 等待客户端接入.......");
            while (true) {
                try {
                    // 2: 阻塞线程获得新连接
                    Socket 客户端接入的请求通道 = 服务端套接字.accept();
                    打印客户端ip(客户端接入的请求通道);
                    // 3: 创建线程负责读取内存数据
                    ThreadUtil.execute(() -> {
                        // 4: 按字节流方式读取数据
                        读取客户端通过网络发送消息(客户端接入的请求通道);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static void 读取客户端通过网络发送消息(Socket 客户端接入的请求通道) {
        int 读取数据长度 = 1024;
        byte[] 单次承载数据容器;
        try {
            final InputStream 客户端输入流 = 客户端接入的请求通道.getInputStream();
            while (true) {
                单次承载数据容器 = IoUtil.readBytes(客户端输入流, 读取数据长度);
                if (单次承载数据容器 == null || 单次承载数据容器.length == 0) {
                    break;
                }
                String 从客户端发送过来的消息 = new String(单次承载数据容器, 0, 读取数据长度).trim();
                服务端输出.控制台("从客户端发送过来的消息{}", 从客户端发送过来的消息);
            }
        } catch (Exception e) {
            输出异常(客户端接入的请求通道.getInetAddress().getHostAddress(), e);
        }
    }

    private static void 打印客户端ip(Socket 客户端接入的请求通道) {
        服务端输出.控制台("有客户端接入, ip: {}", 客户端接入的请求通道.getInetAddress().getHostAddress());
    }

    private static void 输出异常(String 客户端ip, Exception e) {
        if (StrUtil.containsAnyIgnoreCase(ExceptionUtil.getMessage(e), "Connection reset")) {
            服务端输出.控制台("有客户端断开连接, 断开ip为[{}].", 客户端ip);
        } else {
            throw new RuntimeException(e);
        }
    }

}

package Z_0019_Netty入门IO通讯.S1_IO编程实现;

import cn.hutool.core.io.IoUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class K1_服务端 {

    public static void main(String[] args) throws Exception {
        ServerSocket 服务端 = new ServerSocket(8000);

        // 1: 启动
        new Thread(() -> {
            /*
             * 在传统的 IO 模型中，每个连接创建成功之后都需要一个线程来维护。
             * 每个线程包含一个 while 死循环，那么 1w 个连接对应 1w 个线程，继而 1w 个 while 死循环，这就带来如下几个问题：
             * 线程资源受限：线程是操作系统中非常宝贵的资源，同一时刻有大量的线程处于阻塞状态是非常严重的资源浪费，操作系统耗不起
             * 线程切换效率低下：单机 CPU 核数固定，线程爆炸之后操作系统频繁进行线程切换，应用性能急剧下降。
             * 除了以上两个问题，IO 编程中，我们看到数据读写是以字节流为单位。
             */
            while (true) {
                try {
                    // 2: 阻塞线程获得新连接
                    Socket 客户端连接 = 服务端.accept();

                    final InputStream inputStream = 客户端连接.getInputStream();
                    // 3: 创建线程负责读取内存数据
                    new Thread(() -> {
                        // 4: 按字节流方式读取数据
                        int 读取数据长度 = 1024;
                        byte[] 数据;
                        while ((数据 = IoUtil.readBytes(inputStream, 读取数据长度)) != null) {
                            System.out.println(new String(数据, 0, 读取数据长度).trim());
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

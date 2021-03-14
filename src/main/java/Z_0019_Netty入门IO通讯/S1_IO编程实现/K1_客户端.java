package Z_0019_Netty入门IO通讯.S1_IO编程实现;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class K1_客户端 {
    
    public static void main(String[] args) throws Exception {
        /*
         * 伪代码:
         *      事先获取服务端ip和端口.
         *      创建该端口socker客户端程序.
         *      打开客户端连接输出通道(内存输出到网络).
         *      使用将消息转码成字节流.
         *      并输出到输出通道.
         */
        ThreadUtil.execute(() -> {
            try {
                System.out.println("启动客户端.");
                Socket 客户端 = new Socket("127.0.0.1", 8000);
                try (OutputStream 客户端输出流 = 客户端.getOutputStream()) {
                    // 循环 
                    while (true) {
                        // 需要持续输出，所以不能关闭流
                        String msg = new Date() + ": hello world";
                        IoUtil.write(客户端输出流, false, msg.getBytes());
                        System.out.println("客户端发送消息: " + msg);
                        // 每隔两秒
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (Exception e) {
                    String message = ExceptionUtil.getMessage(e);
                    if (StrUtil.containsAnyIgnoreCase(message, "Connection reset by peer: socket write error")) {
                        System.out.println("服务端停止服务, 本客户端也停止.");
                        System.exit(0);
                    } else {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    
}

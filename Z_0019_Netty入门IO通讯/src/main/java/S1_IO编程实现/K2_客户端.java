package S1_IO编程实现;

import Z_utils.输出;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class K2_客户端 {

    public static final String 消息 = DateUtil.now() + ": hello world";
    private static Socket 客户端套接字;

    /*
     * 伪代码:
     *      事先获取服务端ip和端口.
     *      创建该端口socker客户端程序.
     *      打开客户端连接输出通道(内存输出到网络).
     *      使用将消息转码成字节流.
     *      并输出到输出通道.
     */
    public static void main(String[] args) throws Exception {
        运行();
    }

    public static void 运行() {
        获取套接字();
        向服务端发送消息();
    }

    private static void 获取套接字() {
        输出.客户端.控制台输出("启动");
        try {
            客户端套接字 = new Socket("127.0.0.1", 8000);
        } catch (IOException e) {
            输出异常(e);
        }
    }

    private static void 向服务端发送消息() {
        try (OutputStream 客户端输出流 = 客户端套接字.getOutputStream()) {
            // 循环
            while (true) {
                // 需要持续输出，所以不能关闭流
                IoUtil.write(客户端输出流, false, 消息.getBytes());
                输出.客户端.控制台输出("发送消息[{}]", 消息);
                // 每隔两秒
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e) {
            输出异常(e);
        }
    }

    private static void 输出异常(Exception e) {
        String message = ExceptionUtil.getMessage(e);
        if (StrUtil.containsAnyIgnoreCase(message, "Connection reset by peer: socket write error")) {
            输出.客户端.控制台输出("服务端停止服务, 自己也停止.");
            System.exit(0);
        } else {
            throw new RuntimeException(e);
        }
    }

}

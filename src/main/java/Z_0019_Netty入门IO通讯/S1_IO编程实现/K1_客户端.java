package Z_0019_Netty入门IO通讯.S1_IO编程实现;

import cn.hutool.core.io.IoUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class K1_客户端 {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket 客户端 = new Socket("127.0.0.1", 8000);
                OutputStream outputStream = 客户端.getOutputStream();
                while (true) {
                    try {
                        // 需要持续输出，所以不能关闭流
                        IoUtil.write(outputStream, false, (new Date() + ": hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

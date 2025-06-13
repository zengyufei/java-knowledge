package K001_socket实现;

import cn.hutool.core.lang.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class G5_服务端 {
    public static void main(String[] args) throws IOException {
        K001_socket实现.G2_服务接口 服务接口 = new K001_socket实现.G3_服务接口实现();
        try (ServerSocket socket服务端 = new ServerSocket(K001_socket实现.G1_常量.端口)) {
            Console.log("Server 端启动：{}:{}", K001_socket实现.G1_常量.地址, K001_socket实现.G1_常量.端口);
            while (true) {
                try (Socket 监听到socket = socket服务端.accept()) {
                    // 将请求反序列化
                    ObjectInputStream 输入本地内存 = new ObjectInputStream(监听到socket.getInputStream());
                    Object 输出结果 = 输入本地内存.readObject();
                    Console.log("Request is: {}", 输出结果);
                    // 调用服务
                    final String 计算结果 = 服务接口.字符串转换大写(输出结果.toString());
                    // 返回结果
                    ObjectOutputStream 本地内存输出 = new ObjectOutputStream(监听到socket.getOutputStream());
                    本地内存输出.writeObject(计算结果);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

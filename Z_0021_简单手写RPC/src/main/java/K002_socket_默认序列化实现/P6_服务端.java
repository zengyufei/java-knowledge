package K002_socket_默认序列化实现;

import cn.hutool.core.lang.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class P6_服务端 {
    public static void main(String[] args) throws IOException {
        P3_服务接口 服务接口 = new P4_服务接口实现();
        try (ServerSocket 监听 = new ServerSocket(P1_常量.端口)) {
            Console.log("Server 端启动：{}:{}", P1_常量.地址, P1_常量.端口);
            while (true) {
                try (Socket 监听到socket = 监听.accept()) {
                    // 将请求反序列化
                    ObjectInputStream 输入到本地内存 = new ObjectInputStream(监听到socket.getInputStream());
                    Object 输出结果 = 输入到本地内存.readObject();
                    Console.log("Request is: {}", 输出结果);
                    // 调用服务
                    int 计算结果 = 0;
                    if (输出结果 instanceof P2_入参 入参) {
                        计算结果 = 服务接口.计算加法(入参.get参数一(), 入参.get参数二());
                    }
                    // 返回结果
                    ObjectOutputStream 从本地内存输出 = new ObjectOutputStream(监听到socket.getOutputStream());
                    从本地内存输出.writeObject(计算结果);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

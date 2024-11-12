package K001_websocket实现;

import cn.hutool.core.lang.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class G6_服务端 {
    public static void main(String[] args) throws IOException {
        G3_服务接口 服务接口 = new G4_服务接口实现();
        try (ServerSocket 监听 = new ServerSocket(G1_常量.端口)) {
            Console.log("Server 端启动：{}:{}", G1_常量.地址, G1_常量.端口);
            while (true) {
                try (Socket 监听到socket = 监听.accept()) {
                    // 将请求反序列化
                    ObjectInputStream 输出流 = new ObjectInputStream(监听到socket.getInputStream());
                    Object 输出结果 = 输出流.readObject();
                    Console.log("Request is: {}", 输出结果);
                    // 调用服务
                    int 计算结果 = 0;
                    if (输出结果 instanceof G2_入参 入参) {
                        计算结果 = 服务接口.计算加法(入参.get参数一(), 入参.get参数二());
                    }
                    // 返回结果
                    ObjectOutputStream 另外一个输出流 = new ObjectOutputStream(监听到socket.getOutputStream());
                    另外一个输出流.writeObject(计算结果);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

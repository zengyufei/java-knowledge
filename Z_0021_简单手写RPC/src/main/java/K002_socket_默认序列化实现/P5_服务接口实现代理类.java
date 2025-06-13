package K002_socket_默认序列化实现;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class P5_服务接口实现代理类 implements P3_服务接口 {

    @Override
    public int 计算加法(int 参数一, int 参数二) {
        try (Socket socket客户端 = new Socket(P1_常量.地址, P1_常量.端口)) {

            // 将请求序列化
            P2_入参 入参 = new P2_入参(参数一, 参数二);
            ObjectOutputStream 从本地内存输出 = new ObjectOutputStream(socket客户端.getOutputStream());

            // 将请求发给服务提供方
            从本地内存输出.writeObject(入参);

            // 将响应体反序列化
            ObjectInputStream 输入到本地内存 = new ObjectInputStream(socket客户端.getInputStream());
            Object 请求结果 = 输入到本地内存.readObject();

            if (请求结果 instanceof Integer) {
                return (Integer) 请求结果;
            } else {
                throw new RuntimeException();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

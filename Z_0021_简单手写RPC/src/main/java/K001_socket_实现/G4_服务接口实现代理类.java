package K001_socket实现;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class G4_服务接口实现代理类 implements K001_socket实现.G2_服务接口 {

    @Override
    public String 字符串转换大写(String 参数) {
        try (Socket socket = new Socket(K001_socket实现.G1_常量.地址, K001_socket实现.G1_常量.端口)) {

            // 将请求序列化
            ObjectOutputStream 本地内存输出 = new ObjectOutputStream(socket.getOutputStream());

            // 将请求发给服务提供方
            本地内存输出.writeObject(参数);

            // 将响应体反序列化
            ObjectInputStream 输入本地内存 = new ObjectInputStream(socket.getInputStream());
            Object 请求结果 = 输入本地内存.readObject();

            if (请求结果 instanceof String) {
                return (String) 请求结果;
            } else {
                throw new RuntimeException();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

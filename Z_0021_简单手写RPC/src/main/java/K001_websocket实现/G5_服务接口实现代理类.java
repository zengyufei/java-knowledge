package K001_websocket实现;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class G5_服务接口实现代理类 implements G3_服务接口 {

    @Override
    public int 计算加法(int 参数一, int 参数二) {
        try (Socket socket = new Socket(G1_常量.地址, G1_常量.端口)) {

            // 将请求序列化
            G2_入参 入参 = new G2_入参(参数一, 参数二);
            ObjectOutputStream 输出流 = new ObjectOutputStream(socket.getOutputStream());

            // 将请求发给服务提供方
            输出流.writeObject(入参);

            // 将响应体反序列化
            ObjectInputStream 另外一个输出流 = new ObjectInputStream(socket.getInputStream());
            Object 请求结果 = 另外一个输出流.readObject();

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

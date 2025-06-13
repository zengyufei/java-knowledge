package K003_socket_JSON序列化实现;

import cn.hutool.json.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class L5_服务接口实现代理类 implements L3_服务接口 {

    @Override
    public int 计算加法(int 参数一, int 参数二) {
        try (Socket socket客户端 = new Socket(L1_常量.地址, L1_常量.端口)) {

            // 将请求序列化
            L2_入参 入参 = new L2_入参(参数一, 参数二);
            PrintWriter 从本地内存输出 = new PrintWriter(socket客户端.getOutputStream(), true, StandardCharsets.UTF_8);

            // 将请求发给服务提供方
            从本地内存输出.println(JSONUtil.toJsonStr(入参));

            // 将响应体反序列化
            InputStreamReader 输入到本地内存 = new InputStreamReader(socket客户端.getInputStream());
            final BufferedReader bufferedReader = new BufferedReader(输入到本地内存);
            final String line = bufferedReader.readLine();
            if (line != null) {
                final L2_出参 出参 = JSONUtil.parseObj(line).toBean(L2_出参.class);
                return 出参.get计算结果();
            }
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

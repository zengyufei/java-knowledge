package K003_socket_JSON序列化实现;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class L6_服务端 {
    public static void main(String[] args) throws IOException {
        L3_服务接口 服务接口 = new L4_服务接口实现();
        try (ServerSocket 监听 = new ServerSocket(L1_常量.端口)) {
            Console.log("Server 端启动：{}:{}", L1_常量.地址, L1_常量.端口);
            while (true) {
                try (Socket 监听到socket = 监听.accept()) {
                    // 将请求反序列化
                    @Cleanup final InputStreamReader 外部写入内存流 = new InputStreamReader(监听到socket.getInputStream(), StandardCharsets.UTF_8);
                    @Cleanup final BufferedReader bufferedReader = new BufferedReader(外部写入内存流);
                    final String line = bufferedReader.readLine();
                    Console.log("Request is: {}", line);
                    if (line != null) {
                        final JSONObject entries = JSONUtil.parseObj(line);
                        final L2_入参 入参 = entries.toBean(L2_入参.class);
                        // 调用服务
                        int 计算结果 = 服务接口.计算加法(入参.get参数一(), 入参.get参数二());
                        final L2_出参 出参 = new L2_出参(计算结果);
                        // 返回结果
                        @Cleanup final PrintWriter 本地内存输出流 = new PrintWriter(监听到socket.getOutputStream(), true, StandardCharsets.UTF_8); // true 表示自动 flush
                        本地内存输出流.println(JSONUtil.toJsonStr(出参));
                        System.out.println("Response sent: " + 计算结果);
                    } else {
                        System.out.println("客户端发送了空请求或关闭了连接。");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

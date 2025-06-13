package K002_socket_默认序列化实现;

import cn.hutool.core.lang.Console;

import java.io.IOException;

public class P7_客户端 {
    public static void main(String[] args) throws IOException {
        P3_服务接口 服务接口 = new P5_服务接口实现代理类();
        {
            int 计算结果 = 服务接口.计算加法(1, 2);
            Console.log("计算结果: {}", 计算结果);
        }
        {
            int 计算结果 = 服务接口.计算加法(5, 9);
            Console.log("计算结果: {}", 计算结果);
        }
    }

}

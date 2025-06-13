package K001_socket_实现;

import cn.hutool.core.lang.Console;

import java.io.IOException;

public class G6_客户端 {
    public static void main(String[] args) throws IOException {
        K001_socket_实现.G2_服务接口 服务接口 = new K001_socket_实现.G4_服务接口实现代理类();
        {
            final String 计算结果 = 服务接口.字符串转换大写("abcdefg");
            Console.log("计算结果: {}", 计算结果);
        }
        {
            final String 计算结果 = 服务接口.字符串转换大写("oooopppppqqqqqddddzzzaa");
            Console.log("计算结果: {}", 计算结果);
        }
    }

}

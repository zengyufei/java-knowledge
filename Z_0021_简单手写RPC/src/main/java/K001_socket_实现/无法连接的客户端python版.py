import socket
import pickle

# 主要问题出在 Java 端使用 ObjectInputStream 和 ObjectOutputStream 进行序列化/反序列化，
# 而 Python 端使用的是 pickle 模块。

#  序列化机制不兼容：

#  Java 的 ObjectInputStream/ObjectOutputStream 使用的是 Java 自己的二进制序列化协议。
#  Python 的 pickle 模块 使用的是 Python 自己的二进制序列化协议。
#  这两种协议是不兼容的。Python 客户端发送的 pickle 格式数据，Java 服务端是无法正确通过 ObjectInputStream 反序列化的；
#  反之，Java 服务端用 ObjectOutputStream 序列化的数据，Python 客户端也无法通过 pickle.loads() 正确反序列化。
#  ObjectInputStream 的行为：

#  当你创建 new ObjectInputStream(监听到socket.getInputStream()) 时，它会尝试从流中读取 Java 序列化头部信息。
#  如果流中没有这些头部，或者数据不符合 Java 序列化规范，它可能会阻塞或直接失败。
#  在你的场景中，Python 客户端发送的是 pickle 格式的字节流，Java 的 ObjectInputStream 无法识别，
#  这可能导致 readObject() 无法正常读取数据，甚至可能根本没有数据被读取到，所以服务端也就没有数据可以处理并返回。

# 解决方案
# 要实现 Python 客户端和 Java 服务端的通信，你需要选择一个双方都支持的通用数据交换格式，而不是各自语言特有的序列化方式。

# 以下是几种推荐的解决方案：

# 使用 JSON (推荐)
# JSON 是一种轻量级的数据交换格式，几乎所有语言都支持，易于阅读和理解。

#修改思路：

# Python 客户端：
#    将请求数据（字符串）编码为 UTF-8 字节，并发送。
#    接收到字节数据后，解码为 UTF-8 字符串，然后处理。
# Java 服务端：
#    接收到客户端发送的字节数据后，解码为 UTF-8 字符串。
#    将处理结果（字符串）编码为 UTF-8 字节，并发送。

# G1_常量 (Constants)
class G1_常量:
    地址 = '127.0.0.1'  # Localhost
    端口 = 12345  # Port number


# G2_服务接口 (Service Interface)
class G2_服务接口:
    def 字符串转换大写(self, 参数: str) -> str:
        raise NotImplementedError


# G4_服务接口实现代理类 (Service Proxy Implementation)
class G4_服务接口实现代理类(G2_服务接口):
    def 字符串转换大写(self, 参数: str) -> str:
        try:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
                sock.connect((G1_常量.地址, G1_常量.端口))

                # Serialize and send the request
                request_data = pickle.dumps(参数)
                print(f"客户端发送请求数据大小: {len(request_data)} 字节") # 添加日志
                sock.sendall(request_data)

                # Receive and deserialize the response
                response_data = b''
                while True:
                    chunk = sock.recv(4096)
                    if not chunk:
                        print("客户端接收数据完成或服务器关闭连接。") # 添加日志
                        break
                    response_data += chunk
                    print(f"客户端已接收 {len(response_data)} 字节") # 添加日志

                print(f"客户端最终接收到数据大小: {len(response_data)} 字节")  # 添加日志
                request_result = pickle.loads(response_data)

                if isinstance(request_result, str):
                    return request_result
                else:
                    raise RuntimeError("Received unexpected response type from server.")
        except (socket.error, pickle.UnpicklingError) as e:
            raise RuntimeError(f"Error during RPC call: {e}")


# G6_客户端 (Client)
class G6_客户端:
    @staticmethod
    def main():
        服务接口 = G4_服务接口实现代理类()

        # Test case 1
        计算结果1 = 服务接口.字符串转换大写("abcdefg")
        print(f"计算结果: {计算结果1}")

        # Test case 2
        计算结果2 = 服务接口.字符串转换大写("oooopppppqqqqqddddzzzaa")
        print(f"计算结果: {计算结果2}")


if __name__ == '__main__':
    G6_客户端.main()

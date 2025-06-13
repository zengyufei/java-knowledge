import socket
import json
# import pickle # 不再需要 pickle


# G1_常量 (Constants)
class G1_常量:
    地址 = '127.0.0.1'  # Localhost
    端口 = 12347  # Port number


# G2_服务接口 (Service Interface)
class G2_服务接口:
    def 计算加法(self, 参数一: int, 参数二: int) -> int:
        raise NotImplementedError


# G4_服务接口实现代理类 (Service Proxy Implementation)
class G4_服务接口实现代理类(G2_服务接口):
    def 计算加法(self, 参数一: int, 参数二: int) -> int:
        try:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
                sock.connect((G1_常量.地址, G1_常量.端口))

                # Encode the string to bytes and send with a newline character
                # Java的readLine()会读取到换行符，所以这里需要加上\n
                request_payload = {
                    "参数一": 参数一,
                    "参数二": 参数二,
                }

                request_data = (json.dumps(request_payload, ensure_ascii=False) + "\n").encode('utf-8')
                print(f"客户端发送请求数据大小: {len(request_data)} 字节, 内容: {request_data.decode('utf-8').strip()}")
                sock.sendall(request_data)

                # Receive the response as bytes
                response_data = b''
                # 假设响应也是一行，读取到换行符或连接关闭
                # 使用 sock.makefile() 可以像文件一样读取，更方便处理文本流
                with sock.makefile('r', encoding='utf-8') as f:
                    response_line = f.readline().strip() # 读取一行并去除首尾空白，包括换行符


                if response_line:
                    # 将 JSON 响应字符串解析为 Python 对象 (例如，一个数字)
                    response_result = json.loads(response_line)
                    result = response_result.get("计算结果")
                    if isinstance(result, (int, float)): # 检查是否是数字类型
                        return int(result) # 返回整数结果
                    else:
                        raise RuntimeError(f"Received unexpected response type from server: {type(response_result)}")
                else:
                    raise RuntimeError("Received empty or no response from server.")


        except socket.error as e:
            raise RuntimeError(f"Error during RPC call: {e}")
        except json.JSONDecodeError as e:
            raise RuntimeError(f"Error decoding JSON response from server: {e}")
        except Exception as e:
            raise RuntimeError(f"Unexpected error: {e}")


# G6_客户端 (Client)
class G6_客户端:
    @staticmethod
    def main():
        服务接口 = G4_服务接口实现代理类()

        # Test case 1 for 加法
        num1_1, num1_2 = 10, 25
        计算结果1 = 服务接口.计算加法(num1_1, num1_2)
        print(f"计算结果: {num1_1} + {num1_2} = {计算结果1}")

        # Test case 2 for 加法
        num2_1, num2_2 = -5, 120
        计算结果2 = 服务接口.计算加法(num2_1, num2_2)
        print(f"计算结果: {num2_1} + {num2_2} = {计算结果2}")

if __name__ == '__main__':
    # 控制台打印：
    # 客户端发送请求数据大小: 35 字节, 内容: {"参数一": 10, "参数二": 25}
    # 计算结果: 10 + 25 = 35
    # 客户端发送请求数据大小: 36 字节, 内容: {"参数一": -5, "参数二": 120}
    # 计算结果: -5 + 120 = 115
    G6_客户端.main()
package M_16_测试自动重连;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Slf4j
public class K2_消费者 {

    static final String simpleName = K2_消费者.class.getSimpleName();
    static K0_连接管理器 连接管理器 = new K0_连接管理器();

    /**
     * 消费消息
     */
    public static void 监听队列接收mq消息() throws IOException, TimeoutException {
        启动消费者(true);
        // 对消息进行阻断不在往下运行
        System.in.read();
    }

    private static void 启动消费者(boolean 第一次) throws IOException, TimeoutException {
        Channel 信道;
        if (第一次) {
            信道 = 连接管理器.创建信道();
        }
        else {
            信道 = 连接管理器.重连信道();
        }
        监听消费(信道);
    }

    private static void 监听消费(Channel 信道) throws IOException {
        信道.basicConsume(K0_常量.队列名称, true,
                (consumerTag, message) -> {
                    final String 接收消息内容 = new String(message.getBody(), StandardCharsets.UTF_8);
                    Console.log("{}接收到消息: {}", simpleName, 接收消息内容);
                },
                consumerTag -> Console.log("{}消息消费被中断,取消消费消息", simpleName),
                (consumerTag, sig) -> {
                    Console.log("消费者被关闭");
                    while (true) {
                        try {
                            启动消费者(false);
                            break;
                        } catch (IOException | TimeoutException e) {
                            log.warn(ExceptionUtil.stacktraceToString(e));
                        }
                    }
                });
    }


    public static void main(String[] args) throws IOException, TimeoutException {
        监听队列接收mq消息();
    }
}

package M_16_测试自动重连;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;

@Slf4j
@Data
public class K0_连接管理器 {

    private ConnectionFactory 连接工厂;
    private Connection 连接;
    private List<Channel> 信道列表 = new CopyOnWriteArrayList<>();

    public K0_连接管理器() {
        // 1.创建连接工厂
        连接工厂 = new ConnectionFactory();

        // 2.设置IP
        连接工厂.setHost(K0_常量.HOST);

        // 3.设置端口,注意端口不是web界面访问的端口
        连接工厂.setPort(K0_常量.端口);

        // 4.设置用户名和密码
        连接工厂.setUsername(K0_常量.账号);
        连接工厂.setPassword(K0_常量.密码);
        连接工厂.setAutomaticRecoveryEnabled(true); // 自动重连
        连接工厂.setTopologyRecoveryEnabled(true); // 拓扑重连
        连接工厂.setNetworkRecoveryInterval(10000);// 10秒
    }

    private Connection 创建连接() {
        if (连接 != null) {
            return 连接;
        }
        try {
            // 5.创建连接
            连接 = 连接工厂.newConnection();
            Console.log(K0_连接管理器.class.getSimpleName() + " =" + 连接.hashCode());
        } catch (IOException | TimeoutException e) {
            System.err.println("创建连接失败: []" + e.getMessage() + "]");
            throw new RuntimeException(e);
        }
        return 连接;
    }

    public Channel 创建信道() throws IOException {
        if (连接 == null) {
            创建连接();
        }
        try {
            Channel 信道 = 连接.createChannel();
            信道列表.add(信道);
            return 信道;
        } catch (IOException e) {
            System.err.println("创建信道失败: [" + e.getMessage() + "]");
            throw new RuntimeException(e);
        }
    }

    public Channel 重连信道() {
        关闭资源();
        return 重新创建信道();
    }

    private Channel 重新创建信道() {
        while (true) {
            try {
                return 创建信道();
            } catch (Exception e) {
                log.warn(ExceptionUtil.stacktraceToString(e));
            }
        }
    }

    private void 关闭资源() {
        关闭信道列表();
        关闭连接();
    }

    private void 关闭连接() {
        try {
            if (连接 != null && 连接.isOpen()) {
                连接.close();
            }
        } catch (Exception e) {
            log.warn(ExceptionUtil.stacktraceToString(e));
        } finally {
            连接 = null;
        }
    }

    private void 关闭信道列表() {
        try {
            for (Channel 信道 : 信道列表) {
                if (信道 != null && 信道.isOpen()) {
                    信道.close();
                }
            }
        } catch (Exception e) {
            log.warn(ExceptionUtil.stacktraceToString(e));
        } finally {
            信道列表.clear();
        }
    }
}

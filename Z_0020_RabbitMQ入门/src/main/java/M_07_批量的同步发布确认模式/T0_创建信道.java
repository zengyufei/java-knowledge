package M_07_批量的同步发布确认模式;


import cn.hutool.core.lang.Console;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class T0_创建信道 {

    public static Channel 创建信道() {
        // 1.创建连接工厂
        ConnectionFactory 连接工厂 = new ConnectionFactory();

        // 2.设置IP
        连接工厂.setHost(T0_常量.HOST);

        // 3.设置端口,注意端口不是web界面访问的端口
        连接工厂.setPort(T0_常量.端口);

        // 4.设置用户名和密码
        连接工厂.setUsername(T0_常量.账号);
        连接工厂.setPassword(T0_常量.密码);


        Connection 连接 = null;
        Channel 信道 = null;
        try {
            // 5.创建连接
            连接 = 连接工厂.newConnection();
            Console.log(T0_创建信道.class.getSimpleName() + " =" + 连接.hashCode());
        } catch (IOException | TimeoutException e) {
            System.err.println("创建连接失败: []" + e.getMessage() + "]");
            e.printStackTrace();
        }

        try {
            if (连接 != null) {
                // 6.创建信道
                信道 = 连接.createChannel();
            }
        } catch (IOException e) {
            System.err.println("创建信道失败: []" + e.getMessage() + "]");
            e.printStackTrace();
        }
        return 信道;
    }
}

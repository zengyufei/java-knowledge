package S11_Netty用Pipline改造.V5_处理链;

import S11_Netty用Pipline改造.V3_实现层.V6_登录请求数据包;
import S11_Netty用Pipline改造.V3_实现层.V7_登录响应数据包;
import S11_Netty用Pipline改造.V3_实现层.V8_发送消息请求数据包;
import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V15_登录响应处理器 extends SimpleChannelInboundHandler<V7_登录响应数据包> {

    @Override
    public void channelActive(ChannelHandlerContext 上下文) throws Exception {
        输出.客户端.控制台输出("开始登录...");
        V6_登录请求数据包 v6_登录请求数据包 = 构建登录请求包();
        发送消息给对方(上下文, v6_登录请求数据包);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, V7_登录响应数据包 登录响应数据包) throws Exception {
        String 代码 = 登录响应数据包.get代码();
        输出.客户端.控制台输出("服务端回复消息： {}", 代码);
        String 是否成功 = 登录响应数据包.get是否成功();
        if ("true".equals(是否成功)) {
            输出.客户端.控制台输出("请输入要发给服务器的消息：");
            Scanner 控制台 = new Scanner(System.in);
            String 输入一行 = 控制台.nextLine();
            V8_发送消息请求数据包 v8_发送消息请求数据包 = 构建消息请求包(输入一行);
            发送消息给对方(上下文, v8_发送消息请求数据包);
        } else {
            输出.客户端.控制台输出("重新登录...");
            V6_登录请求数据包 v6_登录请求数据包 = 构造可以登录成功请求包();
            发送消息给对方(上下文, v6_登录请求数据包);
        }
    }

    private V6_登录请求数据包 构建登录请求包() {
        V6_登录请求数据包 登录请求数据包 = new V6_登录请求数据包();
        登录请求数据包.set账号("admin1");
        登录请求数据包.set密码("admin");
        登录请求数据包.set姓名("管理员");
        return 登录请求数据包;
    }

    private V6_登录请求数据包 构造可以登录成功请求包() {
        V6_登录请求数据包 登录请求数据包 = new V6_登录请求数据包();
        登录请求数据包.set姓名("管理员");
        登录请求数据包.set账号("admin");
        登录请求数据包.set密码("admin");
        return 登录请求数据包;
    }

    private V8_发送消息请求数据包 构建消息请求包(String 输入一行) {
        V8_发送消息请求数据包 发送消息请求数据包 = new V8_发送消息请求数据包();
        发送消息请求数据包.set消息(输入一行);
        return 发送消息请求数据包;
    }

    private void 发送消息给对方(ChannelHandlerContext 上下文, V8_发送消息请求数据包 发送消息请求数据包) {
        上下文.channel().writeAndFlush(发送消息请求数据包);
    }

    private void 发送消息给对方(ChannelHandlerContext 上下文, V6_登录请求数据包 登录请求数据包) {
        上下文.channel().writeAndFlush(登录请求数据包);
    }

}

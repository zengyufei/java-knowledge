package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_处理链;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M2_接口层.M3_数据包抽象层;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_实现层.M5_登录请求数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_实现层.M6_登录响应数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_实现层.M7_发送消息请求数据;
import Z_utils.输出;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class M15_登录响应处理器 extends SimpleChannelInboundHandler<M6_登录响应数据> {

    @Override
    public void channelActive(ChannelHandlerContext 上下文) throws Exception {
        输出.客户端.控制台("客户端启动成功，趁机发送登录请求到服务端。");
        M5_登录请求数据 登录请求数据 = new M5_登录请求数据("admin", "11111111111111", "管理员");
        输出回复对方(上下文, 登录请求数据);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, M6_登录响应数据 登录响应数据) throws Exception {
        输出.客户端.控制台("收到来自服务端响应： 登录响应。");
        String 响应编码 = 登录响应数据.get响应编码();
        String 是否成功 = 登录响应数据.get是否成功();
        String 失败消息 = 登录响应数据.get失败消息();
        if (校验响应码(响应编码, "200")) {
            输出.客户端.控制台("登录成功，成功消息：{} {} {}。", 响应编码, 是否成功, 失败消息);
            输出.客户端.控制台("给服务器发送一条测试消息: 你好，服务器。");
            M7_发送消息请求数据 m7_发送消息请求数据 = new M7_发送消息请求数据(1, "你好，服务器");
            输出回复对方(上下文, m7_发送消息请求数据);
        } else if (校验响应码(响应编码, "500")) {
            输出.客户端.控制台("登录失败，失败原因：{} {}。", 响应编码, 失败消息);
            输出.客户端.控制台("来自服务端登录失败响应不可怕，可怕的是我，我还要再试一次...");
            输出.客户端.控制台("再发送登录请求到服务端。");
            M5_登录请求数据 m5_登录请求数据 = new M5_登录请求数据("admin", "admin", "管理员");
            输出回复对方(上下文, m5_登录请求数据);
        } else {
            throw new RuntimeException("未知编码");
        }
    }

    private boolean 校验响应码(String 响应编码, String s) {
        return StrUtil.equals(响应编码, s);
    }

    private void 输出回复对方(ChannelHandlerContext 上下文, M3_数据包抽象层 请求数据) {
        上下文.channel().writeAndFlush(请求数据);
    }

}

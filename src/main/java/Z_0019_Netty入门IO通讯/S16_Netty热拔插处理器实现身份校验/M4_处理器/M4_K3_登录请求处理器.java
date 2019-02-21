package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K3_登录响应数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K3_登录请求数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_工具类包.M5_K1_登录判断工具类;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class M4_K3_登录请求处理器 extends SimpleChannelInboundHandler<M1_K3_登录请求数据> {

    @Override
    protected void channelRead0(ChannelHandlerContext 通道处理上下文, M1_K3_登录请求数据 登录请求数据) throws Exception {
        Channel 这条已经三次握手的tcp通道 = 通道处理上下文.channel();
        Console.log("收到来自客户端请求： 请求登录。");
        String 账号 = 登录请求数据.get账号();
        String 密码 = 登录请求数据.get密码();
        String 姓名 = 登录请求数据.get姓名();
        Console.log("登录信息：账号 {} 密码 {} 姓名 {} ", 账号, 密码, 姓名);

        Console.log("进行登录账密判断...");
        if (StrUtil.equalsIgnoreCase(账号, "admin") && StrUtil.equalsIgnoreCase(密码, "admin")) {
            Console.log("用户 {} 登录成功，密码正确。设置登录成功状态并返回。", 账号);
            这条已经三次握手的tcp通道.writeAndFlush(new M1_K3_登录响应数据("200", "登录成功!"));
        } else {
            Console.log("用户 {} 登录失败，账密不正确。", 账号);
            这条已经三次握手的tcp通道.writeAndFlush(new M1_K3_登录响应数据("500", "登录失败!").set失败消息("账密不正确"));
        }

        M5_K1_登录判断工具类.设置为登录成功(这条已经三次握手的tcp通道);
    }
}

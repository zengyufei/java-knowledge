package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C5_处理链;

import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_实现层.C6_登录请求数据包;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_实现层.C7_登录响应数据包;
import Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C3_实现层.C8_发送消息请求数据包;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class C6_登录响应处理器 extends SimpleChannelInboundHandler<C7_登录响应数据包> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Console.log("开始登录...");
        C6_登录请求数据包 登录请求数据包 = new C6_登录请求数据包();
        登录请求数据包.set账号("admin1");
        登录请求数据包.set密码("admin");
        登录请求数据包.set姓名("管理员");
        ctx.channel().writeAndFlush(登录请求数据包);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, C7_登录响应数据包 登录响应数据包) throws Exception {
        String 代码 = 登录响应数据包.get代码();
        Console.log("服务端回复消息： {}", 代码);

        String 是否成功 = 登录响应数据包.get是否成功();
        if ("true".equals(是否成功)) {
            Console.log("请输入要发给服务器的消息：");
            Scanner 控制台 = new Scanner(System.in);
            String 输入一行 = 控制台.nextLine();
            C8_发送消息请求数据包 发送消息请求数据包 = new C8_发送消息请求数据包();
            发送消息请求数据包.set消息(输入一行);
            ctx.channel().writeAndFlush(发送消息请求数据包);
        } else {
            Console.log("重新登录...");
            C6_登录请求数据包 登录请求数据包 = new C6_登录请求数据包();
            登录请求数据包.set姓名("管理员");
            登录请求数据包.set账号("admin");
            登录请求数据包.set密码("admin");
            ctx.channel().writeAndFlush(登录请求数据包);
        }
    }

}

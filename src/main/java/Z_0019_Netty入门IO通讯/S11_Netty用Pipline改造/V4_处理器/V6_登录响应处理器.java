package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V4_处理器;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_发送消息请求数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_登录响应数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_登录请求数据包;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 描述：
 * @author zengyufei
 */
public class V6_登录响应处理器 extends SimpleChannelInboundHandler<V3_登录响应数据包> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Console.log("开始登录...");
        V3_登录请求数据包 登录请求数据包 = new V3_登录请求数据包();
        登录请求数据包.set账号("admin1");
        登录请求数据包.set密码("admin");
        登录请求数据包.set姓名("管理员");
        ctx.channel().writeAndFlush(登录请求数据包);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3_登录响应数据包 登录响应数据包) throws Exception {
        String 代码 = 登录响应数据包.get代码();
        Console.log("服务端回复消息： {}", 代码);

        String 是否成功 = 登录响应数据包.get是否成功();
        if ("true".equals(是否成功)) {
            String 服务器你好 = "服务器你好";
            Console.log("客户端发送一条消息： {}", 服务器你好);
            V3_发送消息请求数据包 发送消息请求数据包 = new V3_发送消息请求数据包();
            发送消息请求数据包.set消息(服务器你好);
            ctx.channel().writeAndFlush(发送消息请求数据包);
        } else {
            Console.log("重新登录...");
            V3_登录请求数据包 登录请求数据包 = new V3_登录请求数据包();
            登录请求数据包.set姓名("管理员");
            登录请求数据包.set账号("admin");
            登录请求数据包.set密码("admin");
            ctx.channel().writeAndFlush(登录请求数据包);
        }
    }

}

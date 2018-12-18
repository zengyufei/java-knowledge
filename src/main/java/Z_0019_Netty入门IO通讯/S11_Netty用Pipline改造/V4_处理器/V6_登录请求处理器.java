package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V4_处理器;

import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_登录响应数据包;
import Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_数据包.V3_登录请求数据包;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 描述：
 * @author zengyufei
 */
public class V6_登录请求处理器 extends SimpleChannelInboundHandler<V3_登录请求数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V3_登录请求数据包 登录请求数据包) throws Exception {
        Console.log("收到一条登录请求，开始认证......");
        String 姓名 = 登录请求数据包.get姓名();
        String 密码 = 登录请求数据包.get密码();
        String 账号 = 登录请求数据包.get账号();

        V3_登录响应数据包 登录响应数据包 = new V3_登录响应数据包();
        if ("admin".equals(账号) && "admin".equals(密码)) {
            Console.log("{} 登录成功! ", 姓名);
            登录响应数据包.set是否成功("true");
            登录响应数据包.set代码("登录成功！");
        }else{
            Console.log("账号 {} 认证失败！", 账号);
            登录响应数据包.set是否成功("false");
            登录响应数据包.set代码("账号密码错误！");
        }
        ctx.channel().writeAndFlush(登录响应数据包);
    }

}

package S11_Netty用Pipline改造.V5_处理链;

import S11_Netty用Pipline改造.V3_实现层.V6_登录请求数据包;
import S11_Netty用Pipline改造.V3_实现层.V7_登录响应数据包;
import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class V14_登录请求处理器 extends SimpleChannelInboundHandler<V6_登录请求数据包> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V6_登录请求数据包 登录请求数据包) throws Exception {
        输出.服务端.控制台输出("收到一条登录请求，开始认证......");
        String 姓名 = 登录请求数据包.get姓名();
        String 密码 = 登录请求数据包.get密码();
        String 账号 = 登录请求数据包.get账号();

        V7_登录响应数据包 登录响应数据包 = new V7_登录响应数据包();
        boolean result = 校验是否登录成功(密码, 账号);
        if (result) {
            输出.服务端.控制台输出("{} 登录成功! ", 姓名);
            登录响应数据包.set是否成功("true");
            登录响应数据包.set代码("登录成功！");
        } else {
            输出.服务端.控制台输出("账号 {} 认证失败！", 账号);
            登录响应数据包.set是否成功("false");
            登录响应数据包.set代码("账号密码错误！");
        }
        ctx.channel().writeAndFlush(登录响应数据包);
    }

    private boolean 校验是否登录成功(String 密码, String 账号) {
        return "admin".equals(账号) && "admin".equals(密码);
    }

}

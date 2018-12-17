package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 描述：
 * @author zengyufei
 */
public class F6_服务端逻辑处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf 数据载体 = (ByteBuf) msg;
        F1_抽象数据包 解码的请求数据包 = F5_解码.解码(数据载体);
        if (解码的请求数据包 instanceof F4_登录请求数据包) {
            F4_登录请求数据包 登录请求数据包 = (F4_登录请求数据包) 解码的请求数据包;
            F4_登录响应数据包 登录响应数据包 = new F4_登录响应数据包();
            if (valid(登录请求数据包)) {
                System.out.println(登录请求数据包.真实姓名 + " 登录成功。");
                登录响应数据包.状态 = "true";
                登录响应数据包.是否成功 = "成功";
            }else{
                System.out.println(登录请求数据包.真实姓名 + " 登录失败！");
                登录响应数据包.状态 = "false";
                登录响应数据包.是否成功 = "失败";
            }
            ByteBuf 编码后的响应数据包 = F5_编码.编码(ctx.alloc(), 登录响应数据包);
            ctx.writeAndFlush(编码后的响应数据包);
        } else if (解码的请求数据包 instanceof F4_发送消息请求数据包) {
            String 消息 = ((F4_发送消息请求数据包) 解码的请求数据包).消息;
            System.out.println( "收到客户发来消息：[" + 消息 + "]");
            F4_发送消息响应数据包 发送消息响应数据包 = new F4_发送消息响应数据包();
            if (消息.contains("你好")) {
                发送消息响应数据包.消息 = "上帝客户，你好！";
            }else{
                发送消息响应数据包.消息 = "你是谁？";
            }
            ByteBuf 编码后的消息响应载体 = F5_编码.编码(ctx.alloc(), 发送消息响应数据包);
            ctx.writeAndFlush(编码后的消息响应载体);
        }
    }

    private boolean valid(F4_登录请求数据包 登录请求数据包) {
        String 账号 = 登录请求数据包.账号;
        String 密码 = 登录请求数据包.密码;
        return "admin".equals(账号) && "admin".equals(密码);
    }
}

package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F1_抽象数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F5_编码;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F5_解码;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F9_登录状态记录工具类;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 描述：
 * @author zengyufei
 */
public class F6_服务端逻辑处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        ByteBuf 数据载体 = (ByteBuf) 消息;
        F1_抽象数据包 解码的请求数据包 = F5_解码.解码(数据载体);
        if (解码的请求数据包 instanceof F4_登录请求数据包) {
            System.out.println("有人登录，进行认证。");
            F4_登录请求数据包 登录请求数据包 = (F4_登录请求数据包) 解码的请求数据包;
            F4_登录响应数据包 登录响应数据包 = new F4_登录响应数据包();
            if (验证登录(登录请求数据包)) {
                F9_登录状态记录工具类.记录登录状态(上下文.channel());
                System.out.println(登录请求数据包.姓名 + " 认证成功。");
                登录响应数据包.代码 = "true";
                登录响应数据包.是否成功 = "成功";
            } else {
                System.out.println(登录请求数据包.姓名 + " 认证失败！");
                登录响应数据包.代码 = "false";
                登录响应数据包.是否成功 = "失败";
            }
            ByteBuf 编码后的响应数据包 = F5_编码.编码(上下文.alloc(), 登录响应数据包);
            上下文.writeAndFlush(编码后的响应数据包);
            return;
        }
        if (F9_登录状态记录工具类.是否登录(上下文.channel())) {
            System.out.println("是已经登录的用户发来消息！");
        } else {
            System.out.println("有未知用户请求，已拦截！");
            F4_发送消息响应数据包 发送消息响应数据包 = new F4_发送消息响应数据包();
            发送消息响应数据包.消息 = "未登录！请登录！";
            ByteBuf 编码后的消息响应载体 = F5_编码.编码(上下文.alloc(), 发送消息响应数据包);
            上下文.writeAndFlush(编码后的消息响应载体);
            return;
        }
        if (解码的请求数据包 instanceof F4_发送消息请求数据包) {
            String 解码的消息 = ((F4_发送消息请求数据包) 解码的请求数据包).消息;
            System.out.println("收到客户发来消息：[" + 解码的消息 + "]");
            F4_发送消息响应数据包 发送消息响应数据包 = new F4_发送消息响应数据包();
            解码的消息 = 解码的消息.replace("在吗", "在的");
            解码的消息 = 解码的消息.replace("吗", "");
            解码的消息 = 解码的消息.replace("?", "!");
            解码的消息 = 解码的消息.replace("？", "！");
            解码的消息 = 解码的消息.replace("你", "我");
            发送消息响应数据包.消息 = 解码的消息;
            ByteBuf 编码后的消息响应载体 = F5_编码.编码(上下文.alloc(), 发送消息响应数据包);
            上下文.writeAndFlush(编码后的消息响应载体);
        }
    }

    private boolean 验证登录(F4_登录请求数据包 登录请求数据包) {
        String 账号 = 登录请求数据包.账号;
        String 密码 = 登录请求数据包.密码;
        return "admin".equals(账号) && "admin".equals(密码);
    }
}

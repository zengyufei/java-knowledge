package S9_Netty客户端与服务端收发消息.F1_处理链;

import S9_Netty客户端与服务端收发消息.F0_协议.F2_接口层.F3_抽象数据包;
import S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F6_登录请求数据包;
import S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F7_登录响应数据包;
import S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F8_发送消息请求数据包;
import S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F9_发送消息响应数据包;
import S9_Netty客户端与服务端收发消息.F0_协议.F4_应用层.F10_编码;
import S9_Netty客户端与服务端收发消息.F0_协议.F4_应用层.F11_解码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class F1_服务端逻辑处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        F3_抽象数据包 解码的请求数据包 = 解码数据((ByteBuf) 消息);
        if (解码的请求数据包 instanceof F6_登录请求数据包) {
            System.out.println("有人登录，进行认证。");
            F6_登录请求数据包 登录请求数据包 = (F6_登录请求数据包) 解码的请求数据包;
            boolean 验证登录状态 = 验证登录(登录请求数据包);
            记录用户登录缓存(上下文, 验证登录状态);
            打印登录成功失败日志(登录请求数据包, 验证登录状态);
            F7_登录响应数据包 登录响应数据包 = 构造登录请求返回响应包(验证登录状态);
            回复对方消息(上下文, F10_编码.编码(上下文.alloc(), 登录响应数据包));
            return;
        }
        boolean 读取登录状态 = F3_登录状态记录工具类.是否登录(上下文.channel());
        打印已登录用户日志(读取登录状态);
        if (!读取登录状态) {
            F9_发送消息响应数据包 发送消息响应数据包 = 构造发送消息返回响应包();
            回复对方消息(上下文, F10_编码.编码(上下文.alloc(), 发送消息响应数据包));
            return;
        }
        if (解码的请求数据包 instanceof F8_发送消息请求数据包) {
            F9_发送消息响应数据包 发送消息响应数据包 = 解码并构造发送消息响应包((F8_发送消息请求数据包) 解码的请求数据包);
            回复对方消息(上下文, F10_编码.编码(上下文.alloc(), 发送消息响应数据包));
        }
    }

    private F3_抽象数据包 解码数据(ByteBuf 消息) {
        ByteBuf 数据载体 = 消息;
        F3_抽象数据包 解码的请求数据包 = F11_解码.解码(数据载体);
        return 解码的请求数据包;
    }

    private boolean 验证登录(F6_登录请求数据包 登录请求数据包) {
        String 账号 = 登录请求数据包.get账号();
        String 密码 = 登录请求数据包.get密码();
        return "admin".equals(账号) && "admin".equals(密码);
    }

    private void 打印已登录用户日志(boolean 读取登录状态) {
        if (读取登录状态) {
            System.out.println("是已经登录的用户发来消息！");
        } else {
            System.out.println("有未知用户请求，已拦截！");
        }
    }

    private void 记录用户登录缓存(ChannelHandlerContext 上下文, boolean 验证登录状态) {
        if (验证登录状态) {
            F3_登录状态记录工具类.记录登录状态(上下文.channel());
        }
    }

    private void 打印登录成功失败日志(F6_登录请求数据包 登录请求数据包, boolean 验证登录状态) {
        if (验证登录状态) {
            System.out.println(登录请求数据包.get姓名() + " 认证成功。");
        } else {
            System.out.println(登录请求数据包.get姓名() + " 认证失败！");
        }
    }

    private void 回复对方消息(ChannelHandlerContext 上下文, ByteBuf 编码) {
        ByteBuf 编码后的响应数据包 = 编码;
        上下文.writeAndFlush(编码后的响应数据包);
    }

    private F7_登录响应数据包 构造登录请求返回响应包(boolean 验证登录状态) {
        F7_登录响应数据包 登录响应数据包 = new F7_登录响应数据包();
        if (验证登录状态) {
            登录响应数据包.set代码("true");
            登录响应数据包.set是否成功("成功");
        } else {
            登录响应数据包.set代码("false");
            登录响应数据包.set是否成功("失败");
        }
        return 登录响应数据包;
    }

    private F9_发送消息响应数据包 构造发送消息返回响应包() {
        F9_发送消息响应数据包 发送消息响应数据包 = new F9_发送消息响应数据包();
        发送消息响应数据包.set消息("未登录！请登录！");
        return 发送消息响应数据包;
    }

    private F9_发送消息响应数据包 解码并构造发送消息响应包(F8_发送消息请求数据包 解码的请求数据包) {
        String 解码的消息 = 解码的请求数据包.get消息();
        System.out.println("收到客户发来消息：[" + 解码的消息 + "]");
        F9_发送消息响应数据包 发送消息响应数据包 = new F9_发送消息响应数据包();
        解码的消息 = 解码的消息.replace("在吗", "在的");
        解码的消息 = 解码的消息.replace("吗", "");
        解码的消息 = 解码的消息.replace("?", "!");
        解码的消息 = 解码的消息.replace("？", "！");
        解码的消息 = 解码的消息.replace("你", "我");
        发送消息响应数据包.set消息(解码的消息);
        return 发送消息响应数据包;
    }
}

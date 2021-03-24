package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F1_处理链;

import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F2_接口层.F3_抽象数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F6_登录请求数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F7_登录响应数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F8_发送消息请求数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F3_实现层.F9_发送消息响应数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F4_应用层.F10_编码;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F4_应用层.F11_解码;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Scanner;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class F2_客户端逻辑处理器 extends ChannelInboundHandlerAdapter {

    /**
     * 连接上后直接请求登录
     */
    @Override
    public void channelActive(ChannelHandlerContext 上下文) throws Exception {
        System.out.println("连接上了，马上登录！");
        F6_登录请求数据包 登录请求数据包 = 构造错误登录请求包();
        发送数据包(上下文, F10_编码.编码(上下文.alloc(), 登录请求数据包));
    }


    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        F3_抽象数据包 解码后的数据包 = 解码消息((ByteBuf) 消息);
        if (解码后的数据包 instanceof F7_登录响应数据包) {
            F7_登录响应数据包 f7_登录响应数据包 = (F7_登录响应数据包) 解码后的数据包;
            boolean result = 校验登录是否成功(f7_登录响应数据包);
            打印校验结果日志(result);
            if (result) {
                给服务器发送自定义消息(上下文);
            } else {
                // 因为密码错误，所以重新登录
                F6_登录请求数据包 登录请求数据包 = 构造登录可以成功请求包();
                发送数据包(上下文, F10_编码.编码(上下文.alloc(), 登录请求数据包));
            }
        } else if (解码后的数据包 instanceof F9_发送消息响应数据包) {
            System.out.println("收到服务端发来消息：[" + ((F9_发送消息响应数据包) 解码后的数据包).get消息() + "]");
            // 给服务器发送消息
            给服务器发送自定义消息(上下文);
        }
    }

    private void 打印校验结果日志(boolean result) {
        if (result) {
            System.out.println("登录是否成功：[是]");
        } else {
            System.out.println("因为密码错误，所以重新登录");
        }
    }

    private F3_抽象数据包 解码消息(ByteBuf 消息) {
        ByteBuf 数据载体 = 消息;
        F3_抽象数据包 解码后的数据包 = F11_解码.解码(数据载体);
        return 解码后的数据包;
    }

    private boolean 校验登录是否成功(F7_登录响应数据包 f7_登录响应数据包) {
        String 代码 = f7_登录响应数据包.get代码();
        String 是否成功 = f7_登录响应数据包.get是否成功();
        return "true".equals(代码) && "成功".equals(是否成功);
    }

    private F6_登录请求数据包 构造错误登录请求包() {
        F6_登录请求数据包 登录请求数据包 = new F6_登录请求数据包();
        登录请求数据包.set姓名("管理员");
        登录请求数据包.set账号("admin");
        登录请求数据包.set密码("admin1");
        return 登录请求数据包;
    }

    private F6_登录请求数据包 构造登录可以成功请求包() {
        F6_登录请求数据包 登录请求数据包 = new F6_登录请求数据包();
        登录请求数据包.set姓名("管理员");
        登录请求数据包.set账号("admin");
        登录请求数据包.set密码("admin");
        return 登录请求数据包;
    }

    private void 给服务器发送自定义消息(ChannelHandlerContext 上下文) {
        // 登录成功后给服务器发送消息
        F8_发送消息请求数据包 发送消息请求数据包 = new F8_发送消息请求数据包();
        System.out.println("请输入：");
        Scanner 控制台输入器 = new Scanner(System.in);
        String 控制台输入一行 = 控制台输入器.nextLine();
        发送消息请求数据包.set消息(控制台输入一行);
        //发送消息请求数据包.消息 = "你好，服务器.";
        发送数据包(上下文, F10_编码.编码(上下文.alloc(), 发送消息请求数据包));
    }

    private void 发送数据包(ChannelHandlerContext 上下文, ByteBuf 编码) {
        ByteBuf 编码后的请求数据包 = 编码;
        上下文.writeAndFlush(编码后的请求数据包);
    }
}

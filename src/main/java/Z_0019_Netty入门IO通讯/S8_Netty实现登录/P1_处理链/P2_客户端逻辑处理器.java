package Z_0019_Netty入门IO通讯.S8_Netty实现登录.P1_处理链;

import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P2_接口层.P3_抽象数据包;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P3_实现层.P6_登录请求数据包;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P3_实现层.P7_登录响应数据包;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P4_应用层.P8_编码;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P4_应用层.P9_解码;
import Z_utils.客户端输出;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class P2_客户端逻辑处理器 extends ChannelInboundHandlerAdapter {

    /**
     * 连接上时执行
     */
    @Override
    public void channelActive(ChannelHandlerContext 上下文) throws Exception {
        客户端输出.控制台(new Date() + ": 我要准备登录了!");
        P6_登录请求数据包 登录请求数据包 = 构建错误登录请求包();
        发送登录请求包(上下文, 登录请求数据包);
    }

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        P3_抽象数据包 解码后的数据 = 消息解码((ByteBuf) 消息);
        if (解码后的数据 instanceof P7_登录响应数据包) {
            P7_登录响应数据包 p7_登录响应数据包 = (P7_登录响应数据包) 解码后的数据;
            boolean result = 核对登录是否成功(p7_登录响应数据包);
            打印日志(result);
            if (!result) {
                P6_登录请求数据包 登录请求数据包 = 构建可以登录成功请求包();
                发送重新登录请求包(上下文, 登录请求数据包);
            }
        }
    }

    private P3_抽象数据包 消息解码(ByteBuf 消息) {
        ByteBuf 接受到的数据 = 消息;
        P3_抽象数据包 解码后的数据 = P9_解码.解码(接受到的数据);
        return 解码后的数据;
    }

    private void 打印日志(boolean result) {
        if (!result) {
            客户端输出.控制台(new Date() + ": 解析后说我因为密码错误重新登录");
        } else {
            客户端输出.控制台(new Date() + ": 解析后, 我终于登录成功啦!");
        }
    }

    private P6_登录请求数据包 构建错误登录请求包() {
        P6_登录请求数据包 登录请求数据包 = new P6_登录请求数据包();
        登录请求数据包.set姓名("root");
        登录请求数据包.set密码("root");
        登录请求数据包.set用户名("root");
        登录请求数据包.set版本号((byte) 1);
        return 登录请求数据包;
    }

    private P6_登录请求数据包 构建可以登录成功请求包() {
        P6_登录请求数据包 登录请求数据包 = new P6_登录请求数据包();
        登录请求数据包.set姓名("admin");
        登录请求数据包.set密码("admin");
        登录请求数据包.set用户名("admin");
        登录请求数据包.set版本号((byte) 1);
        return 登录请求数据包;
    }

    private void 发送登录请求包(ChannelHandlerContext 上下文, P6_登录请求数据包 登录请求数据包) {
        ByteBuf 登录数据包 = P8_编码.编码(上下文.alloc(), 登录请求数据包);
        登录数据包.writeBytes("你好，服务器！".getBytes(StandardCharsets.UTF_8));
        上下文.channel().writeAndFlush(登录数据包);
    }

    private boolean 核对登录是否成功(P7_登录响应数据包 解码后的数据) {
        P7_登录响应数据包 登录响应数据包 = 解码后的数据;
        客户端输出.控制台("收到服务端返回消息, {}", JSONObject.toJSONString(登录响应数据包));
        String 消息内容 = 登录响应数据包.get消息();
        String 是否成功 = 登录响应数据包.get是否成功();
        return StrUtil.equals(是否成功, "成功");
    }

    private void 发送重新登录请求包(ChannelHandlerContext 上下文, P6_登录请求数据包 登录请求数据包) {
        ByteBuf 登录数据包 = P8_编码.编码(上下文.alloc(), 登录请求数据包);
        上下文.channel().writeAndFlush(登录数据包);
    }

}

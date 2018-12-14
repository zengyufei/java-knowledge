package Z_0019_Netty入门IO通讯.S8_Netty实现登录;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class P9_客户端逻辑处理器 extends ChannelInboundHandlerAdapter {

    /**
     * 连接上时执行
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端准备要登录");

        // 1: 准备登录数据包
        P4_登录请求数据包 登录请求数据包 = new P4_登录请求数据包();
        登录请求数据包.set姓名("admin");
        登录请求数据包.set密码("admin");
        登录请求数据包.set用户名("admin");
        登录请求数据包.set版本号((byte) 1);

        ByteBuf 要发送的数据 = P7_编码.编码(ctx.alloc(), 登录请求数据包);
        // 2: 填充要发送的数据
        要发送的数据.writeBytes("你好，服务器！".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(要发送的数据);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf 接受到的数据 = (ByteBuf) msg;

        P3_抽象数据包 解码后的数据 = P7_解码.解码(接受到的数据);
        System.out.println(JSONObject.toJSONString(解码后的数据));
    }
}

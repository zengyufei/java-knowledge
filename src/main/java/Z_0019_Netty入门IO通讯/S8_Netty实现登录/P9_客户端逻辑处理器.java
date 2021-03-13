package Z_0019_Netty入门IO通讯.S8_Netty实现登录;

import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P3_抽象数据包;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P7_编码;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P7_解码;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
        登录请求数据包.set姓名("root");
        登录请求数据包.set密码("root");
        登录请求数据包.set用户名("root");
        登录请求数据包.set版本号((byte) 1);

        ByteBuf 登录数据包 = P7_编码.编码(ctx.alloc(), 登录请求数据包);
        // 2: 填充要发送的数据
        登录数据包.writeBytes("你好，服务器！".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(登录数据包);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf 接受到的数据 = (ByteBuf) msg;
        

        P3_抽象数据包 解码后的数据 = P7_解码.解码(接受到的数据);
        if (解码后的数据 instanceof P4_登录响应数据包) {
            System.out.println(JSONObject.toJSONString(解码后的数据));
            P4_登录响应数据包 登录响应数据包 = (P4_登录响应数据包) 解码后的数据;
            String 消息 = 登录响应数据包.get消息();
            String 是否成功 = 登录响应数据包.get是否成功();
            if (StrUtil.equals(是否成功, "成功")) {
                System.out.println(JSONUtil.toJsonPrettyStr(登录响应数据包));
            }else{
                System.out.println(new Date() + ": 客户端因为密码错误重新登录");
    
                // 1: 准备登录数据包
                P4_登录请求数据包 登录请求数据包 = new P4_登录请求数据包();
                登录请求数据包.set姓名("admin");
                登录请求数据包.set密码("admin");
                登录请求数据包.set用户名("admin");
                登录请求数据包.set版本号((byte) 1);
    
                ByteBuf 登录数据包 = P7_编码.编码(ctx.alloc(), 登录请求数据包);
                ctx.channel().writeAndFlush(登录数据包);
            }
        }
    }
}

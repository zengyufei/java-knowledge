package Z_0019_Netty入门IO通讯.S8_Netty实现登录;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class P8_服务端逻辑处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 1: 收到消息
        ByteBuf 接收的数据 = (ByteBuf) msg;
        P3_抽象数据包 解码后的数据 = P7_解码.解码(接收的数据);
        if (解码后的数据 instanceof P4_登录请求数据包) {
            P4_登录请求数据包 登录请求数据包 = (P4_登录请求数据包) 解码后的数据;
            System.out.println("服务端接收到来自客户端连接时发送的数据：" + JSONObject.toJSONString(登录请求数据包));
        }

        // 2: 回复
        P4_登录响应数据包 登录响应数据包 = new P4_登录响应数据包();
        登录响应数据包.set是否成功("成功");
        登录响应数据包.set消息("登录成功！");
        登录响应数据包.set版本号((byte) 2);

        ByteBuf 编码后的响应数据 = P7_编码.编码(ctx.alloc(), 登录响应数据包);
        ctx.channel().writeAndFlush(编码后的响应数据);

    }
}

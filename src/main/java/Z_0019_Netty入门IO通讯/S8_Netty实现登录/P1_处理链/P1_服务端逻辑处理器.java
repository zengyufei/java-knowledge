package Z_0019_Netty入门IO通讯.S8_Netty实现登录.P1_处理链;

import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P2_接口层.P3_抽象数据包;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P3_实现层.P6_登录请求数据包;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P3_实现层.P7_登录响应数据包;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P4_应用层.P8_编码;
import Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议.P4_应用层.P9_解码;
import Z_utils.服务端输出;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class P1_服务端逻辑处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object 消息) throws Exception {
        // 1: 收到消息
        P3_抽象数据包 解码后的数据 = 消息解码((ByteBuf) 消息);
        if (解码后的数据 instanceof P6_登录请求数据包) {
            P6_登录请求数据包 p6_登录请求数据包 = (P6_登录请求数据包) 解码后的数据;
            boolean result = 核对登录信息(p6_登录请求数据包);
            if (result) {
                P7_登录响应数据包 登录响应数据包 = 构造登录响应数据包("成功", "登录成功！");
                回复对方(上下文, 登录响应数据包);
            } else {
                P7_登录响应数据包 登录响应数据包 = 构造登录响应数据包("失败", "登录失败！");
                回复对方(上下文, 登录响应数据包);
            }
        }
    }

    private P3_抽象数据包 消息解码(ByteBuf 消息) {
        ByteBuf 接收的数据 = 消息;
        P3_抽象数据包 解码后的数据 = P9_解码.解码(接收的数据);
        return 解码后的数据;
    }

    private void 回复对方(ChannelHandlerContext 上下文, P7_登录响应数据包 登录响应数据包) {
        ByteBuf 编码后的响应数据 = P8_编码.编码(上下文.alloc(), 登录响应数据包);
        上下文.channel().writeAndFlush(编码后的响应数据);
    }

    private P7_登录响应数据包 构造登录响应数据包(String 状态, String 消息) {
        P7_登录响应数据包 登录响应数据包 = new P7_登录响应数据包();
        登录响应数据包.set是否成功(状态);
        登录响应数据包.set消息(消息);
        登录响应数据包.set版本号((byte) 2);
        return 登录响应数据包;
    }

    private boolean 核对登录信息(P6_登录请求数据包 解码后的数据) {
        P6_登录请求数据包 登录请求数据包 = 解码后的数据;
        服务端输出.控制台("接收到来自客户端连接时发送的数据：" + JSONObject.toJSONString(登录请求数据包));
        String 姓名 = 登录请求数据包.get姓名();
        String 密码 = 登录请求数据包.get密码();
        String 用户名 = 登录请求数据包.get用户名();
        return StrUtil.equals(姓名, "admin")
                && StrUtil.equals(用户名, "admin")
                && StrUtil.equals(密码, "admin");
    }

}

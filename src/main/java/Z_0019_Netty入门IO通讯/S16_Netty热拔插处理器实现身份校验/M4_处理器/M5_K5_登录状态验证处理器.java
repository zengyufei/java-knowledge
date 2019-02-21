package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K4_发送消息响应数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M5_工具类包.M5_K1_登录判断工具类;
import cn.hutool.core.lang.Console;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class M5_K5_登录状态验证处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext 通道处理上下文, Object msg) throws Exception {
        if (M5_K1_登录判断工具类.判断是否登录成功(通道处理上下文.channel())) {
            // 自删除
            通道处理上下文.pipeline().remove(this);
            super.channelRead(通道处理上下文, msg);
        }else{
            通道处理上下文.channel().writeAndFlush(new M1_K4_发送消息响应数据("未登录"));
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext 通道处理上下文) {
        if (M5_K1_登录判断工具类.判断是否登录成功(通道处理上下文.channel())) {
            Console.log("已登录，移除登录状态校验处理器。");
        } else {
            Console.log("登录超时失效！！！！！");
        }
    }
}

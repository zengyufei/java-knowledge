package S16_Netty热拔插处理器实现身份校验.M5_处理链;

import S16_Netty热拔插处理器实现身份校验.M3_实现层.M8_发送消息响应数据;
import S16_Netty热拔插处理器实现身份校验.M6_工具类包.M19_登录判断工具类;
import Z_utils.输出;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class M18_登录状态验证处理器 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext 上下文, Object msg) throws Exception {
        if (M19_登录判断工具类.判断是否登录成功(上下文.channel())) {
            // 自删除
            上下文.pipeline().remove(this);
            super.channelRead(上下文, msg);
        } else {
            上下文.channel().writeAndFlush(new M8_发送消息响应数据("未登录"));
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext 上下文) {
        if (M19_登录判断工具类.判断是否登录成功(上下文.channel())) {
            输出.服务端.控制台输出("已登录，移除登录状态校验处理器。");
        } else {
            输出.服务端.控制台输出("登录超时失效！！！！！");
        }
    }
}

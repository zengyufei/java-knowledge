package S16_Netty热拔插处理器实现身份校验.M5_处理链;

import S16_Netty热拔插处理器实现身份校验.M3_实现层.M5_登录请求数据;
import S16_Netty热拔插处理器实现身份校验.M3_实现层.M6_登录响应数据;
import S16_Netty热拔插处理器实现身份校验.M6_工具类包.M19_登录判断工具类;
import Z_utils.输出;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class M14_登录请求处理器 extends SimpleChannelInboundHandler<M5_登录请求数据> {

    @Override
    protected void channelRead0(ChannelHandlerContext 上下文, M5_登录请求数据 登录请求数据) throws Exception {
        Channel 已经三次握手的tcp通道 = 上下文.channel();
        输出.服务端.控制台输出("收到来自客户端请求： 请求登录。");
        String 账号 = 登录请求数据.get账号();
        String 密码 = 登录请求数据.get密码();
        String 姓名 = 登录请求数据.get姓名();
        输出.服务端.控制台输出("登录信息：账号 {} 密码 {} 姓名 {} ", 账号, 密码, 姓名);
        输出.服务端.控制台输出("进行登录账密判断...");
        if (校验账号密码(账号, 密码)) {
            输出.服务端.控制台输出("用户 {} 登录成功，密码正确。设置登录成功状态并返回。", 账号);
            M6_登录响应数据 m6_登录响应数据 = new M6_登录响应数据("200", "登录成功!");
            输出回复对方(已经三次握手的tcp通道, m6_登录响应数据);
        } else {
            输出.服务端.控制台输出("用户 {} 登录失败，账密不正确。", 账号);
            M6_登录响应数据 m6_登录响应数据 = new M6_登录响应数据("500", "登录失败!");
            m6_登录响应数据.set失败消息("账密不正确");
            输出回复对方(已经三次握手的tcp通道, m6_登录响应数据);
        }
        M19_登录判断工具类.设置为登录成功(已经三次握手的tcp通道);
    }

    private boolean 校验账号密码(String 账号, String 密码) {
        return StrUtil.equalsIgnoreCase(账号, "admin") && StrUtil.equalsIgnoreCase(密码, "admin");
    }

    private ChannelFuture 输出回复对方(Channel 已经三次握手的tcp通道, M6_登录响应数据 m6_登录响应数据) {
        return 已经三次握手的tcp通道.writeAndFlush(m6_登录响应数据);
    }
}

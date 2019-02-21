package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M4_处理器;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K3_登录响应数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K3_登录请求数据;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包.M1_K4_发送消息请求数据;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class M4_K3_登录响应处理器 extends SimpleChannelInboundHandler<M1_K3_登录响应数据> {

    @Override
    public void channelActive(ChannelHandlerContext 通道处理上下文) throws Exception {
        Console.log("客户端启动成功，趁机发送登录请求到服务端。");
        M1_K3_登录请求数据 登录请求数据 = new M1_K3_登录请求数据("admin", "11111111111111", "管理员");
        通道处理上下文.channel().writeAndFlush(登录请求数据);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext 通道处理上下文, M1_K3_登录响应数据 登录响应数据) throws Exception {
        Console.log("收到来自服务端响应： 登录响应。");
        String 响应编码 = 登录响应数据.get响应编码();
        String 是否成功 = 登录响应数据.get是否成功();
        String 失败消息 = 登录响应数据.get失败消息();
        if (StrUtil.equals(响应编码, "200")) {
            Console.log("登录成功，成功消息：{} {} {}。", 响应编码, 是否成功, 失败消息);
            Console.log("给服务器发送一条测试消息: 你好，服务器。");
            通道处理上下文.channel().writeAndFlush(new M1_K4_发送消息请求数据(1,"你好，服务器"));
        }else if(StrUtil.equals(响应编码, "500")){
            Console.log("登录失败，失败原因：{} {}。", 响应编码, 失败消息);
            Console.log("来自服务端登录失败响应不可怕，可怕的是我，我还要再试一次...");
            Console.log("再发送登录请求到服务端。");
            M1_K3_登录请求数据 登录请求数据 = new M1_K3_登录请求数据("admin", "admin", "管理员");
            通道处理上下文.channel().writeAndFlush(登录请求数据);
        }else{
            throw new RuntimeException("未知编码");
        }
    }

}

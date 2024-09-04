package S15_NettyChannelHandler生命周期;

public class H4_编排运行 {

    /**
     * 端口[8000]绑定成功!
     * H3_ChannelHandler生命周期打印.<init>()
     * H3_ChannelHandler生命周期打印.isSharable()
     * H3_ChannelHandler生命周期打印.handlerAdded()
     * H3_ChannelHandler生命周期打印.channelRegistered()
     * H3_ChannelHandler生命周期打印.channelActive()
     * 开始登录...
     * H3_ChannelHandler生命周期打印.channelRead()
     * 收到一条登录请求，开始认证......
     * 账号 admin1 认证失败！
     * H3_ChannelHandler生命周期打印.channelReadComplete()
     * 服务端回复消息： 账号密码错误！
     * 重新登录...
     * H3_ChannelHandler生命周期打印.channelRead()
     * 收到一条登录请求，开始认证......
     * 管理员 登录成功!
     * H3_ChannelHandler生命周期打印.channelReadComplete()
     * 服务端回复消息： 登录成功！
     * 请输入要发给服务器的消息：
     * <p>
     * 进程已结束，退出代码为 -1
     */
    public static void main(String[] args) {
        H1_服务端.运行();
        H2_客户端.运行();
    }
}

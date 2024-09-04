package S13_Netty拆包粘包解决;

public class L3_编排运行 {

    /**
     * 端口[8000]绑定成功!
     * 发送登录请求。。。。
     * 发送登录请求。。。。
     * 发送登录请求。。。。
     * 发送登录请求。。。。
     * 发送登录请求。。。。
     * 发送登录请求。。。。
     */
    public static void main(String[] args) {
        L1_服务端.运行();
        L2_客户端.运行();
    }
}

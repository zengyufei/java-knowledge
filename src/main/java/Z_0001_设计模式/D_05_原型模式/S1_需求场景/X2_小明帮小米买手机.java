package Z_0001_设计模式.D_05_原型模式.S1_需求场景;

public class X2_小明帮小米买手机 {

    public static void main(String[] args) {
        X1_账号 小明的手机激活用的账号 = new X1_账号();
        小明的手机激活用的账号.用户名 = "小明";
        小明的手机激活用的账号.密码 = "woaixiaoming";


        X1_手机 小明的手机 = new X1_手机();
        小明的手机.价格 = "100";
        小明的手机.颜色 = "黑色";
        小明的手机.账号 = 小明的手机激活用的账号;

        // 小明帮小米买手机，顺便用自己的账号激活调试用
        X1_手机 小米的手机 = new X1_手机();
        小米的手机.价格 = 小明的手机.价格;
        小米的手机.颜色 = "白色";
        小米的手机.账号 = 小明的手机.账号;
        // 用自己的账号登录
        小米的手机.账号.用户名 = "小米";
        小米的手机.账号.密码 = "xiaomi";

        // 小明的账号被替换成小米
        System.out.println(小明的手机);
        System.out.println(小米的手机);
    }

}

package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y1_数据包;

public class Y1_3_登录响应数据包 implements Y1_1_抽象数据包 {

    @Override
    public byte 版本() {
        return 1;
    }

    @Override
    public byte 指令() {
        return Y1_2_指令类型.LOGIN_RESPONSE;
    }

    private String 用户名;
    private String 结果;
    private String 错误提示;

    public String get用户名() {
        return 用户名;
    }

    public void set用户名(String 用户名) {
        this.用户名 = 用户名;
    }

    public String get结果() {
        return 结果;
    }

    public void set结果(String 结果) {
        this.结果 = 结果;
    }

    public String get错误提示() {
        return 错误提示;
    }

    public void set错误提示(String 错误提示) {
        this.错误提示 = 错误提示;
    }
}

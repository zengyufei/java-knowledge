package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y1_数据包;

public class Y1_3_登录请求数据包 implements Y1_1_抽象数据包 {

    @Override
    public byte 版本() {
        return 1;
    }

    @Override
    public byte 指令() {
        return Y1_2_指令类型.LOGIN_REQUEST;
    }

    private String 账号;
    private String 密码;
    private String 用户名;

    public String get账号() {
        return 账号;
    }

    public void set账号(String 账号) {
        this.账号 = 账号;
    }

    public String get密码() {
        return 密码;
    }

    public void set密码(String 密码) {
        this.密码 = 密码;
    }

    public String get用户名() {
        return 用户名;
    }

    public void set用户名(String 用户名) {
        this.用户名 = 用户名;
    }
}

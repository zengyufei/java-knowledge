package Z_0019_Netty入门IO通讯.S8_Netty实现登录;

public class P4_登录请求数据包 extends P3_抽象数据包{

    private String 用户名;
    private String 密码;
    private String 姓名;

    @Override
    public Byte 指令() {
        return P3_指令类型.登录请求指令;
    }

    public String get用户名() {
        return 用户名;
    }

    public void set用户名(String 用户名) {
        this.用户名 = 用户名;
    }

    public String get密码() {
        return 密码;
    }

    public void set密码(String 密码) {
        this.密码 = 密码;
    }

    public String get姓名() {
        return 姓名;
    }

    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }
}

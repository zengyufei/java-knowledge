package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包;

public class M1_K3_登录请求数据 extends M1_K1_数据包抽象层 {

    @Override
    public byte 获取指令类型() {
        return M1_K2_指令集合.LOGIN_REQUEST;
    }

    private String 账号;
    private String 密码;
    private String 姓名;

    public M1_K3_登录请求数据(String 账号, String 密码, String 姓名) {
        this.账号 = 账号;
        this.密码 = 密码;
        this.姓名 = 姓名;
    }

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

    public String get姓名() {
        return 姓名;
    }

    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }
}

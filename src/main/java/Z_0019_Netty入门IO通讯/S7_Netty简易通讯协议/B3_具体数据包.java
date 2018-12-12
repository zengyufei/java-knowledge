package Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议;

/**
 * 描述：
 * @author zengyufei
 */
public class B3_具体数据包 extends B1_抽象数据包 {

    //super Byte version;

    private String 用户唯一标识;
    private String 用户名;
    private String 密码;

    @Override
    public Byte 获取指令() {
        return B2_指令类型.登录请求;
    }

    public String get用户唯一标识() {
        return 用户唯一标识;
    }

    public void set用户唯一标识(String 用户唯一标识) {
        this.用户唯一标识 = 用户唯一标识;
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
}

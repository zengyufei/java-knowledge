package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

/**
 * 描述：
 * @author zengyufei
 */
public class F4_登录请求数据包 extends F1_抽象数据包 {

    String 账号;
    String 密码;
    String 真实姓名;

    @Override
    Byte 指令() {
        return F1_指令.登录请求;
    }

    // json 序列化需要 getter/setter

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

    public String get真实姓名() {
        return 真实姓名;
    }

    public void set真实姓名(String 真实姓名) {
        this.真实姓名 = 真实姓名;
    }
}

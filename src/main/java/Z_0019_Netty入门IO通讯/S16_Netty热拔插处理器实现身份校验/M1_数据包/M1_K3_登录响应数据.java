package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包;

public class M1_K3_登录响应数据 extends M1_K1_数据包抽象层 {

    @Override
    public byte 获取指令类型() {
        return M1_K2_指令集合.LOGIN_RESPONSE;
    }

    private String 响应编码;
    private String 是否成功;
    private String 失败消息;

    public M1_K3_登录响应数据(String 响应编码, String 是否成功) {
        this.响应编码 = 响应编码;
        this.是否成功 = 是否成功;
    }

    public String get响应编码() {
        return 响应编码;
    }

    public void set响应编码(String 响应编码) {
        this.响应编码 = 响应编码;
    }

    public String get是否成功() {
        return 是否成功;
    }

    public void set是否成功(String 是否成功) {
        this.是否成功 = 是否成功;
    }

    public String get失败消息() {
        return 失败消息;
    }

    public M1_K3_登录响应数据 set失败消息(String 失败消息) {
        this.失败消息 = 失败消息;
        return this;
    }
}

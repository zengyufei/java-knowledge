package Z_0019_Netty入门IO通讯.S8_Netty实现登录;

public class P4_登录响应数据包 extends P3_抽象数据包{

    private String 消息;
    private String 是否成功;

    @Override
    public Byte 指令() {
        return P3_指令类型.登录响应指令;
    }

    public String get消息() {
        return 消息;
    }

    public void set消息(String 消息) {
        this.消息 = 消息;
    }

    public String get是否成功() {
        return 是否成功;
    }

    public void set是否成功(String 是否成功) {
        this.是否成功 = 是否成功;
    }
}

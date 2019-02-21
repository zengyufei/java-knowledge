package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_数据包;

public class M1_K4_发送消息响应数据 extends M1_K1_数据包抽象层 {

    @Override
    public byte 获取指令类型() {
        return M1_K2_指令集合.SEND_MESSAGE_RESPONSE;
    }

    private int 消息响应次数;
    private String 消息体;

    public M1_K4_发送消息响应数据(String 消息体) {
        this.消息体 = 消息体;
    }

    public M1_K4_发送消息响应数据(int 消息响应次数, String 消息体) {
        this.消息响应次数 = 消息响应次数;
        this.消息体 = 消息体;
    }

    public int get消息响应次数() {
        return 消息响应次数;
    }

    public void set消息响应次数(int 消息响应次数) {
        this.消息响应次数 = 消息响应次数;
    }

    public String get消息体() {
        return 消息体;
    }

    public void set消息体(String 消息体) {
        this.消息体 = 消息体;
    }
}

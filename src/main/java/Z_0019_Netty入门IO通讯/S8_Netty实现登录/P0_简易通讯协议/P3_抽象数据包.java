package Z_0019_Netty入门IO通讯.S8_Netty实现登录.P0_简易通讯协议;

public abstract class P3_抽象数据包 {

    public Byte 版本号 = 1;

    public abstract Byte 指令();

    public Byte get版本号() {
        return 版本号;
    }

    public void set版本号(Byte 版本号) {
        this.版本号 = 版本号;
    }
}

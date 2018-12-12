package Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议;

/**
 * 描述：
 * @author zengyufei
 */
public abstract class B1_抽象数据包 {

    private Byte 版本号 = 1;

    public abstract Byte 获取指令();

    public Byte 版本号() {
        return 版本号;
    }

    public void set版本号(Byte 版本号) {
        this.版本号 = 版本号;
    }
}

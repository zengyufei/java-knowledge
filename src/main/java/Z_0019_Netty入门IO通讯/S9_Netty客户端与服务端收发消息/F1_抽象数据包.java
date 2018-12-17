package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

/**
 * 描述：
 * @author zengyufei
 */
public abstract class F1_抽象数据包 {

    public final static int 魔数 = 0x12345678;

    /**
     * 默认1为发请求，2为响应
     */
    Byte 版本 = 1;

    abstract Byte 指令();

}

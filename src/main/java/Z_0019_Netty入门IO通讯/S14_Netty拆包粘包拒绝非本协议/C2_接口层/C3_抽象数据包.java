package Z_0019_Netty入门IO通讯.S14_Netty拆包粘包拒绝非本协议.C2_接口层;

/**
 * 描述：
 *
 * @author zengyufei
 */
public abstract class C3_抽象数据包 {

    public byte 版本 = 1;

    public abstract byte 指令();

}

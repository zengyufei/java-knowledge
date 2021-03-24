package Z_0019_Netty入门IO通讯.S11_Netty用Pipline改造.V2_接口层;

/**
 * 描述：
 *
 * @author zengyufei
 */
public abstract class V3_抽象数据包 {

    public byte 版本 = 1;

    public abstract byte 指令();

}

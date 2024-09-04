package S7_Netty简易通讯协议.B2_接口层;

import lombok.Data;

/**
 * 描述：
 *
 * @author zengyufei
 */
@Data
public abstract class B3_抽象数据包 {

    private Byte 版本号 = 1;

    public abstract Byte 获取指令();

}

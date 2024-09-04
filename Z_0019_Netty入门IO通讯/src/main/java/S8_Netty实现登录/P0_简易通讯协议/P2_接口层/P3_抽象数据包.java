package S8_Netty实现登录.P0_简易通讯协议.P2_接口层;

import lombok.Data;

@Data
public abstract class P3_抽象数据包 {

    public Byte 版本号 = 1;

    public abstract Byte 指令();

}

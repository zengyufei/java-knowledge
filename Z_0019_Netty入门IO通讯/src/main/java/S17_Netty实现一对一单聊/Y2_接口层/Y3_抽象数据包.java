package S17_Netty实现一对一单聊.Y2_接口层;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author zengyufei
 */
@Data
public abstract class Y3_抽象数据包 implements Serializable {

    private static final long serialVersionUID = 1892161431269095343L;
    public byte 版本 = 1;

    public abstract byte 指令();

}

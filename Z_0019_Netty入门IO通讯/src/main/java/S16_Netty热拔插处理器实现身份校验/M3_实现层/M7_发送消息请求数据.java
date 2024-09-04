package S16_Netty热拔插处理器实现身份校验.M3_实现层;

import S16_Netty热拔插处理器实现身份校验.M1_常量.M1_指令集合;
import S16_Netty热拔插处理器实现身份校验.M2_接口层.M3_数据包抽象层;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class M7_发送消息请求数据 extends M3_数据包抽象层 {

    @Override
    public byte 获取指令类型() {
        return M1_指令集合.SEND_MESSAGE_REQUEST;
    }

    private int 消息请求次数;
    private String 消息体;

    public M7_发送消息请求数据(String 消息体) {
        this.消息体 = 消息体;
    }

}

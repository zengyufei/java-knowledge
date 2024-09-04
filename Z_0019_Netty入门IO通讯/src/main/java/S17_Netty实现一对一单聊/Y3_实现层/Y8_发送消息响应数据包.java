package S17_Netty实现一对一单聊.Y3_实现层;

import S17_Netty实现一对一单聊.Y1_常量.Y1_指令类型;
import S17_Netty实现一对一单聊.Y2_接口层.Y3_抽象数据包;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Y8_发送消息响应数据包 extends Y3_抽象数据包 {

    private static final long serialVersionUID = -2934337519910141236L;

    @Override
    public byte 指令() {
        return Y1_指令类型.SEND_MESSAGE_RESPONSE;
    }

    String 发送方用户id;
    String 接收方用户id;
    String 消息内容;
    String 发送时间;

}

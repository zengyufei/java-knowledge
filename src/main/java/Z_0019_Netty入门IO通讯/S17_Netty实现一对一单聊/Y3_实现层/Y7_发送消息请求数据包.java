package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y3_实现层;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y1_常量.Y1_指令类型;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_接口层.Y3_抽象数据包;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Y7_发送消息请求数据包 extends Y3_抽象数据包 {

    private static final long serialVersionUID = -6022582785973783358L;

    @Override
    public byte 指令() {
        return Y1_指令类型.SEND_MESSAGE_REQUEST;
    }

    String 对方用户id;
    String 消息内容;
    String 发送时间;

}

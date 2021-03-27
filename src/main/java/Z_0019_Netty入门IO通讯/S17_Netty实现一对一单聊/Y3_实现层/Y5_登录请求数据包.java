package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y3_实现层;

import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y1_常量.Y1_指令类型;
import Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y2_接口层.Y3_抽象数据包;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Y5_登录请求数据包 extends Y3_抽象数据包 {

    private static final long serialVersionUID = 328092582419976198L;

    @Override
    public byte 指令() {
        return Y1_指令类型.LOGIN_REQUEST;
    }

    private String 账号;
    private String 密码;
    private String 用户名;

}

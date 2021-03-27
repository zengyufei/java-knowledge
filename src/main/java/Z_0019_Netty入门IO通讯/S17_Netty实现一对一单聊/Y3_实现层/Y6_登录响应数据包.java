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
public class Y6_登录响应数据包 extends Y3_抽象数据包 {

    private static final long serialVersionUID = -5974105971939211471L;

    @Override
    public byte 指令() {
        return Y1_指令类型.LOGIN_RESPONSE;
    }

    private String 用户id;
    private String 用户名;
    private String 成功状态;
    private String 错误提示;

}

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
public class M5_登录请求数据 extends M3_数据包抽象层 {

    @Override
    public byte 获取指令类型() {
        return M1_指令集合.LOGIN_REQUEST;
    }

    String 账号;
    String 密码;
    String 姓名;

}

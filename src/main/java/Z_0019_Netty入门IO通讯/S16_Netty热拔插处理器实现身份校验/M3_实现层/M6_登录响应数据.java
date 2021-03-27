package Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M3_实现层;

import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M1_常量.M1_指令集合;
import Z_0019_Netty入门IO通讯.S16_Netty热拔插处理器实现身份校验.M2_接口层.M3_数据包抽象层;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class M6_登录响应数据 extends M3_数据包抽象层 {

    @Override
    public byte 获取指令类型() {
        return M1_指令集合.LOGIN_RESPONSE;
    }

    private String 响应编码;
    private String 是否成功;
    private String 失败消息;

    public M6_登录响应数据(String 响应编码, String 是否成功) {
        this.响应编码 = 响应编码;
        this.是否成功 = 是否成功;
    }

}

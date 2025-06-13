package K005_Netty_简单RPC实现;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Q2_返回参 implements Serializable {

    @Serial
    private static final long serialVersionUID = 3420751004355300996L;

    private Boolean 成功状态;
    private Integer 计算结果;

}
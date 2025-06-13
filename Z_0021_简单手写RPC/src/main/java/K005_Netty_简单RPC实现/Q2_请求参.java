package K005_Netty_简单RPC实现;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Q2_请求参 implements Serializable {

    @Serial
    private static final long serialVersionUID = 6420751004355300996L;

    private int 参数一;
    private int 参数二;

}

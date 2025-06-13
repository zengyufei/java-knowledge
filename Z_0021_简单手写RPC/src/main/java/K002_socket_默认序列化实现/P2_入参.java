package K002_socket_默认序列化实现;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class P2_入参 implements Serializable {

    @Serial
    private static final long serialVersionUID = 6420751004355300996L;

    private int 参数一;
    private int 参数二;

}

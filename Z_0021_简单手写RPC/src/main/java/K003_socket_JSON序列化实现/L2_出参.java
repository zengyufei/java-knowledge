package K003_socket_JSON序列化实现;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class L2_出参 implements Serializable {

    @Serial
    private static final long serialVersionUID = 3420751004355300996L;

    private int 计算结果;

}

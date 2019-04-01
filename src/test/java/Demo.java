import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReflectUtil;

/**
 * 枚举父类
 */
interface BaseEnum {
    default String getMark() {
        return ReflectUtil.getFieldValue(this, "状态").toString();
    }
}

enum ErrorCodeEnum implements BaseEnum {
    正常("启用"),
    失败("停用");
    private String 状态;
    ErrorCodeEnum(String 状态) {
        this.状态 = 状态;
    }
}

public class Demo {
    public static void main(String[] args) {
        ErrorCodeEnum 具象枚举 = ErrorCodeEnum.正常;
        Console.log("{}, {}", 具象枚举, 具象枚举.getMark());
        BaseEnum 多态形式枚举 = ErrorCodeEnum.失败;
        Console.log("{}, {}", 多态形式枚举, 多态形式枚举.getMark());
    }
    // 输出结果：
    // 正常, 启用
    // 失败, 停用
}

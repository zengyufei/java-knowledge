package Z_utils;

import cn.hutool.core.util.StrUtil;

public class 服务端输出 {

    public static void 控制台(String 内容) {
        System.out.println("服务端: " + 内容);
    }

    public static void 控制台(String 内容, Object... 参数) {
        System.out.println("服务端: " + StrUtil.format(内容, 参数));
    }

    public static void 控制台输出异常(String 内容) {
        System.err.println("服务端: " + 内容);
    }

    public static void 控制台输出异常(String 内容, Object... 参数) {
        System.err.println("服务端: " + StrUtil.format(内容, 参数));
    }

}

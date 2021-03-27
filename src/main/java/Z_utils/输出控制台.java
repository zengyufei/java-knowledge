package Z_utils;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class 输出控制台 {

    String 名称;

    private 输出控制台() {
    }

    private 输出控制台(String 名称) {
        this.名称 = 名称;
    }

    public static 输出控制台 valueOf(String 名称) {
        return new 输出控制台(名称);
    }

    public void 控制台(String 内容) {
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.out.println(名称 + "(" + lineNumber + "): " + 内容);
    }

    public void 控制台不换行(String 内容) {
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.out.print(名称 + "(" + lineNumber + "): " + 内容);
    }

    public void 控制台(String 内容, Object... 参数) {
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.out.println(名称 + "(" + lineNumber + "): " + StrUtil.format(内容, 参数));
    }

    public void 控制台输出异常(String 内容) {
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.err.println(名称 + "(" + lineNumber + "): " + 内容);
    }

    public void 控制台输出异常(String 内容, Object... 参数) {
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.err.println(名称 + "(" + lineNumber + "): " + StrUtil.format(内容, 参数));
    }
}
package Z_utils;

import cn.hutool.core.lang.Console;
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

    public static 输出控制台 创建(String 名称) {
        return new 输出控制台(名称);
    }

    public void 控制台输出(String 内容) {
        final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        final String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();
        Console.log("[{}] [fun {}():{}] : {}", 名称, methodName, lineNumber, 内容);
    }

    public void 控制台输出不换行(String 内容) {
        final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        final String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();
        Console.print("[{}] [fun {}():{}] : {}", 名称, methodName, lineNumber, 内容);
    }

    public void 控制台输出(String 内容, Object... 参数) {
        final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        final String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();
        Console.log("[{}] [fun {}():{}] : {}", 名称, methodName, lineNumber, StrUtil.format(内容, 参数));
    }

    public void 控制台输出异常(String 内容) {
        final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        final String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();
        Console.error("[{}] [fun {}():{}] : {}", 名称, methodName, lineNumber, 内容);
    }

    public void 控制台输出异常(String 内容, Object... 参数) {
        final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        final String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();
        Console.error("[{}] [fun {}():{}] : {}", 名称, methodName, lineNumber, StrUtil.format(内容, 参数));
    }
}

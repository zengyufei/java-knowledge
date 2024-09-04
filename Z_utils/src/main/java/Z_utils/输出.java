package Z_utils;

import cn.hutool.core.util.StrUtil;

public class 输出 {

    public static final 输出控制台 控制台 = 输出控制台.创建("控制台");

    public static final 输出控制台 服务端 = 输出控制台.创建("服务端");
    public static final 输出控制台 客户端 = 输出控制台.创建("客户端");

    public static void 当前方法简单名() {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println(methodName);
    }

    public static void 当前方法简单名(String suffix) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String tr = stackTraceElement.getMethodName() + " " + suffix;
        System.out.println(tr);
    }

    public static void 当前方法全名() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String tr = StrUtil.subAfter(stackTraceElement.getClassName(), ".", true) + "." + stackTraceElement.getMethodName() + "()";
        System.out.println(tr);
    }

    public static void 当前方法全名(String suffix) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String tr = StrUtil.subAfter(stackTraceElement.getClassName(), ".", true) + "." + stackTraceElement.getMethodName() + "() " + suffix;
        System.out.println(tr);
    }

    public static void 当前类全名() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        System.out.println(stackTraceElement.getClassName());
    }

    public static void 当前类简单名() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String tr = StrUtil.subAfter(stackTraceElement.getClassName(), ".", true);
        System.out.println(tr);
    }


}

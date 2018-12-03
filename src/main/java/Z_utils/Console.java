package Z_utils;

import cn.hutool.core.util.StrUtil;

public class Console {

    public static void getThisMethodName() {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
    }

    public static void getThisMethodName(String suffix) {
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
        String tr = stackTraceElement.getMethodName() + " " + suffix;
        System.out.println(tr);
    }

    public static void getThisMethodFullName() {
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
        String tr = StrUtil.subAfter(stackTraceElement.getClassName(), ".", true) + "." + stackTraceElement.getMethodName() + "()";
        System.out.println(tr);
    }

    public static void getThisMethodFullName(String suffix) {
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
        String tr = StrUtil.subAfter(stackTraceElement.getClassName(), ".", true) + "." + stackTraceElement.getMethodName() + "() " + suffix;
        System.out.println(tr);
    }

}

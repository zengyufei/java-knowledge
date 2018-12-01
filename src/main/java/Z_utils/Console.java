package Z_utils;

public class Console {

    public static String getThisMethodName() {
        return new Exception().getStackTrace()[1].getMethodName();
    }

    public static String getThisMethodName(String suffix) {
        return new Exception().getStackTrace()[1].getMethodName() + suffix;
    }

}

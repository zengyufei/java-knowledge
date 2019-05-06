import cn.hutool.core.io.FileUtil;

import java.io.File;

public class Demo {
    public static void main(String[] args) {
        File file = FileUtil.file("file:.././");
        System.out.println(file.getAbsolutePath());
    }
}

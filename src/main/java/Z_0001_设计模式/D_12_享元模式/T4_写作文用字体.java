package Z_0001_设计模式.D_12_享元模式;

import cn.hutool.core.lang.Console;

public class T4_写作文用字体 {

    public static void main(String[] args) {
        Console.log(T3_字体工厂类.获取新宋体("新宋体").hashCode());
        Console.log(T3_字体工厂类.获取新宋体("新宋体").hashCode());

        Console.log(T3_字体工厂类.获取新宋体("新宋体2").hashCode());
        Console.log(T3_字体工厂类.获取新宋体("新宋体2").hashCode());
    }

}

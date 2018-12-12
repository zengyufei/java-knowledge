package Z_0001_设计模式.D_13_策略模式;

import cn.hutool.core.lang.Console;

/**
 * 描述：
 * 在状态模式中，状态改变是由对象的内部条件决定，外界只需关心其接口，不必关心其状态对象的创建和转化。
 * 策略模式里，采取何种策略由外部条件决定。
 * @author zengyufei
 */
public class J2_八折 implements J1_减价打折接口{

    @Override
    public double 打折(double 原价) {
        Console.log("小降价！");
        return .8 * 原价;
    }
}

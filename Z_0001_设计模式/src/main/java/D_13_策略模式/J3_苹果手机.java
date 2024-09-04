package D_13_策略模式;

import cn.hutool.core.lang.Console;

/**
 * 描述：
 * 在状态模式中，状态改变是由对象的内部条件决定，外界只需关心其接口，不必关心其状态对象的创建和转化。
 * 策略模式里，采取何种策略由外部条件决定。
 * @author zengyufei
 */
public class J3_苹果手机 {

    public double 官方指导价 = 10000.0;

    private J1_减价打折接口 减价打折;

    public J3_苹果手机(J1_减价打折接口 减价打折) {
        this.减价打折 = 减价打折;
    }

    public void 报价() {
        Console.log(减价打折.打折(官方指导价));
    }

}

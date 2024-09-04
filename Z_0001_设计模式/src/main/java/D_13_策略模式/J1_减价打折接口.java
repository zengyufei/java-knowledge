package D_13_策略模式;

/**
 * 描述：
 * 在状态模式中，状态改变是由对象的内部条件决定，外界只需关心其接口，不必关心其状态对象的创建和转化。
 * 策略模式里，采取何种策略由外部条件决定。
 * @author zengyufei
 */
public interface J1_减价打折接口 {

    double 打折(double 原价);

}

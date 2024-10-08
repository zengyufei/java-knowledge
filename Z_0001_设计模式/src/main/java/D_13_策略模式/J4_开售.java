package D_13_策略模式;

/**
 * 描述：
 * 在状态模式中，状态改变是由对象的内部条件决定，外界只需关心其接口，不必关心其状态对象的创建和转化。
 * 策略模式里，采取何种策略由外部条件决定。
 * @author zengyufei
 */
public class J4_开售 {

    public static void main(String[] args) {
        J2_提价百分之二十 提价百分之二十策略 = new J2_提价百分之二十();
        J3_苹果手机 苹果手机 = new J3_苹果手机(提价百分之二十策略);
        苹果手机.报价();

        J2_八折 八折策略 = new J2_八折();
        苹果手机 = new J3_苹果手机(八折策略);
        苹果手机.报价();

        J2_五折 五折策略 = new J2_五折();
        苹果手机 = new J3_苹果手机(五折策略);
        苹果手机.报价();
    }

}

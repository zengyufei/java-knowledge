package Z_0001_设计模式.D_14_模板方法模式;

/**
 * 描述：
 * @author zengyufei
 */
public class R2_泡柠檬茶 extends R1_冲泡饮料 {

    @Override
    protected void 加主料() {
        System.out.println("加入柠檬到杯子");
    }

    @Override
    protected void 加调料() {
        System.out.println("加入蜜糖到杯子");
    }
}

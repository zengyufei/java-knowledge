package Z_0001_设计模式.D_14_模板方法模式;

/**
 * 描述：
 * @author zengyufei
 */
public class R2_泡咖啡 extends R1_冲泡饮料 {

    @Override
    void 加主料() {
        System.out.println("加入咖啡到杯子");
    }

    @Override
    void 加调料() {
        System.out.println("加入木糖醇到杯子");
    }
}

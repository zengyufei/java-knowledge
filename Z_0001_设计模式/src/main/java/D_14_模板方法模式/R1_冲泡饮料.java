package D_14_模板方法模式;

import Z_utils.输出;

/**
 * 描述：
 *
 * @author zengyufei
 */
public abstract class R1_冲泡饮料 {

    final public void 冲泡() {
        烧水();
        加主料();
        加调料();
        倒热水到杯子();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 分割线 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    final private void 倒热水到杯子() {
        输出.当前方法简单名();
    }


    final private void 烧水() {
        输出.当前方法简单名();
    }

    abstract void 加主料();

    abstract void 加调料();

}

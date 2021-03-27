package Z_0001_设计模式.D_02_抽象工厂模式.S2_抽象工厂模式;

import Z_0001_设计模式.D_01_工厂方法模式.S2_简单工厂模式.I1_披萨接口;
import Z_utils.输出;

public abstract class I1_美国披萨 implements I1_披萨接口 {

    @Override
    public void 加水() {
        输出.当前方法简单名("200 毫升。");
    }

    @Override
    public void 加面粉() {
        输出.当前方法简单名("400 克。");
    }

    public abstract void 加酱();
}

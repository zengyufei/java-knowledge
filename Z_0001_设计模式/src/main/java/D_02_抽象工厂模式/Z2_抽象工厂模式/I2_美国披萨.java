package D_02_抽象工厂模式.Z2_抽象工厂模式;

import Z_utils.输出;

public abstract class I2_美国披萨 implements I1_披萨接口 {

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

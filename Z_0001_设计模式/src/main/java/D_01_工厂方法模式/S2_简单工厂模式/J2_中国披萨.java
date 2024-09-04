package D_01_工厂方法模式.S2_简单工厂模式;

import Z_utils.输出;

public class J2_中国披萨 implements J1_披萨接口 {

    @Override
    public void 加水() {
        输出.当前方法简单名("200 毫升。");
    }

    @Override
    public void 加面粉() {
        输出.当前方法简单名("400 克。");
    }
}

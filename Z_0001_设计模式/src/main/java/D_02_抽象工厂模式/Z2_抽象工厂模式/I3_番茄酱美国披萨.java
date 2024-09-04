package D_02_抽象工厂模式.Z2_抽象工厂模式;


import Z_utils.输出;

public class I3_番茄酱美国披萨 extends I2_美国披萨 {

    @Override
    public void 加酱() {
        输出.当前方法简单名("：番茄酱");
    }
}

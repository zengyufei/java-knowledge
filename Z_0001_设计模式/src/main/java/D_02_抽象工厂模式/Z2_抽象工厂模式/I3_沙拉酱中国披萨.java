package D_02_抽象工厂模式.Z2_抽象工厂模式;


import Z_utils.输出;

public class I3_沙拉酱中国披萨 extends I2_中国披萨 {

    @Override
    public void 加酱() {
        输出.当前方法简单名("：沙拉酱");
    }
}

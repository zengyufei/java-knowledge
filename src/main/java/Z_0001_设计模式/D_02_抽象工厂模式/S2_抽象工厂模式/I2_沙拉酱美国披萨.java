package Z_0001_设计模式.D_02_抽象工厂模式.S2_抽象工厂模式;


import Z_utils.输出;

public class I2_沙拉酱美国披萨 extends I1_美国披萨 {

    @Override
    public void 加酱() {
        输出.当前方法简单名("：沙拉酱");
    }
}

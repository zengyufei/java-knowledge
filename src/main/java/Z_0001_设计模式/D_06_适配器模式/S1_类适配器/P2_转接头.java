package Z_0001_设计模式.D_06_适配器模式.S1_类适配器;

import Z_utils.输出;

/**
 * 香港提供的苹果插头为：三孔 电压 200 伏，内地标准为 两孔 电压 220 伏
 * 电阻为 香港 20 欧， 内地 22 欧
 * 有 香港三孔有火线、零线、地线。内地有两孔火线、零线
 */
public class P2_转接头 extends P1_香港插头 implements P2_内地插头标准 {

    @Override
    public void 输出电流到电器() {
        电压 = 220;
        电阻 += 2;
        输出.当前方法简单名(电压 / 电阻 + " 安");
    }

    @Override
    public void 两孔插头() {
        火线();
        零线();
    }

    @Override
    public void 通电() {
        System.out.println("转接头插头通电");
        两孔插头();
        输出电流到电器();
    }

}

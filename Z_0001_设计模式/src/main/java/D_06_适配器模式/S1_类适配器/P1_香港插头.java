package D_06_适配器模式.S1_类适配器;

import Z_utils.输出;

/**
 * 香港提供的苹果插头为：三孔 电压 200 伏 10 安
 * 电阻为 20 欧
 * 有 火线、零线、地线
 */
public class P1_香港插头 {

    public int 电压 = 200;
    public int 电阻 = 20;

    public void 输出电流到电器() {
        输出.当前方法简单名(电压 / 电阻 + " 安");
    }

    public void 三孔插头() {
        火线();
        零线();
        地线();
    }

    public void 火线() {
        输出.当前方法简单名();
    }

    public void 零线() {
        输出.当前方法简单名();
    }

    public void 地线() {
        输出.当前方法简单名();
    }

    public void 通电() {
        System.out.println("香港插头通电");
        三孔插头();
        输出电流到电器();
    }

}

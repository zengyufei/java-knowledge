package Z_0001_设计模式.D_06_适配器模式.S2_对象设配器;

import Z_0001_设计模式.D_06_适配器模式.S1_类适配器.P1_香港插头;
import Z_0001_设计模式.D_06_适配器模式.S1_类适配器.P2_内地插头标准;
import Z_utils.Console;

/**
 * 香港提供的苹果插头为：三孔 电压 200 伏，内地标准为 两孔 电压 220 伏
 * 电阻为 香港 20 欧， 内地 22 欧
 * 有 香港三孔有火线、零线、地线。内地有两孔火线、零线
 */
public class O1_转接头 implements P2_内地插头标准 {

    public P1_香港插头 香港插头;

    public O1_转接头(P1_香港插头 香港插头) {
        this.香港插头 = 香港插头;
    }

    @Override
    public void 输出电流到电器() {
        香港插头.电压 = 220;
        香港插头.电阻 += 2;
        Console.getThisMethodName(香港插头.电压 / 香港插头.电阻 + " 安");
    }

    @Override
    public void 两孔插头() {
        香港插头.火线();
        香港插头.零线();
    }

    @Override
    public void 通电() {
        System.out.println("转接头插头通电");
        两孔插头();
        输出电流到电器();
    }

}

package D_06_适配器模式.S1_类适配器;

/**
 * 内地使用：两孔 电压 220 伏
 * 电阻为 22 欧
 * 只有 火线、零线
 */
public interface P2_内地插头标准 {

    void 输出电流到电器();

    void 两孔插头();

    void 通电();

}

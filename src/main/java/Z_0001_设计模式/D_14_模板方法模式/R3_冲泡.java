package Z_0001_设计模式.D_14_模板方法模式;

/**
 * 描述：
 * @author zengyufei
 */
public class R3_冲泡 {

    public static void main(String[] args) {
        R2_泡咖啡 泡咖啡 = new R2_泡咖啡();
        R2_泡柠檬茶 泡柠檬茶 = new R2_泡柠檬茶();

        泡咖啡.冲泡();
        泡柠檬茶.冲泡();

    }

}

package Z_0001_设计模式.D_06_适配器模式.S1_类适配器;

/**
 * 类适配器通过继承原接口的方式实现适配
 */
public class P3_插入插座通电 {

    public static void main(String[] args) {
        P1_香港插头 香港插头 = new P1_香港插头();
        香港插头.通电();

        System.out.println("-------------------------------");

        P2_转接头 转接头 = new P2_转接头();
        转接头.通电();
    }

}

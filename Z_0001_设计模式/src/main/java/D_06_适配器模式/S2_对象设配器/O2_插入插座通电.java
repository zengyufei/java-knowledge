package D_06_适配器模式.S2_对象设配器;

import D_06_适配器模式.S1_类适配器.P1_香港插头;

/**
 * 对象适配器通过持有原接口对象的方式实现适配
 */
public class O2_插入插座通电 {

    public static void main(String[] args) {
        P1_香港插头 香港插头 = new P1_香港插头();
        香港插头.通电();

        System.out.println("-------------------------------");

        O1_转接头 转接头 = new O1_转接头(香港插头);
        转接头.通电();
    }

}

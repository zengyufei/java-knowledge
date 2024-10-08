package D_08_代理模式.S2_动态代理.Q1_JDK动态代理;

import D_08_代理模式.S1_静态代理.Y1_演员;
import D_08_代理模式.S1_静态代理.Y2_成龙大哥;

public class W2_监制找人拍戏 {

    public static void main(String[] args) {

        int 监制给演戏钱 = 20;
        Y1_演员 成龙大哥 = new Y2_成龙大哥();
        成龙大哥.演戏(监制给演戏钱);

        System.out.println("成龙大哥演戏出名之后....");

        监制给演戏钱 = 20;
        Y1_演员 经纪人 = (Y1_演员) W1_经纪人.代理(成龙大哥);
        经纪人.演戏(监制给演戏钱);

        监制给演戏钱 = 50;
        System.out.println("监制重新谈价格...");
        经纪人.演戏(监制给演戏钱);

    }

}

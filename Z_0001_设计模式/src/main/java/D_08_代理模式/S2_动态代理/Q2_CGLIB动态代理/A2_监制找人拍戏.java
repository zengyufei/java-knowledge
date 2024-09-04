package D_08_代理模式.S2_动态代理.Q2_CGLIB动态代理;

import D_08_代理模式.S1_静态代理.Y1_演员;
import D_08_代理模式.S1_静态代理.Y2_成龙大哥;

public class A2_监制找人拍戏 {

    public static void main(String[] args) {

        int 监制给演戏钱 = 20;
        Y1_演员 成龙大哥 = new Y2_成龙大哥();
        成龙大哥.演戏(监制给演戏钱);

        System.out.println("成龙大哥演戏出名之后....");

        A1_经纪人 经纪人 = new A1_经纪人(成龙大哥);
        Y1_演员 全权代理 = (Y1_演员) 经纪人.代理();
        监制给演戏钱 = 20;
        全权代理.演戏(监制给演戏钱);

        监制给演戏钱 = 50;
        System.out.println("监制重新谈价格...");
        全权代理.演戏(监制给演戏钱);

    }

}

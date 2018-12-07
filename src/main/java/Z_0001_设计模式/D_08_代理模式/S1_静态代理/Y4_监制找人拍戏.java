package Z_0001_设计模式.D_08_代理模式.S1_静态代理;

public class Y4_监制找人拍戏 {

    public static void main(String[] args) {

        int 监制给演戏钱 = 20;
        Y1_演员 成龙大哥 = new Y2_成龙大哥();
        成龙大哥.演戏(监制给演戏钱);

        System.out.println("成龙大哥演戏出名之后....");

        监制给演戏钱 = 20;
        Y3_经纪人 经纪人 = new Y3_经纪人(成龙大哥);
        经纪人.演戏(监制给演戏钱);

        监制给演戏钱 = 50;
        System.out.println("监制重新谈价格...");
        经纪人.演戏(监制给演戏钱);

    }

}

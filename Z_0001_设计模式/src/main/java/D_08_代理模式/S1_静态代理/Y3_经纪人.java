package D_08_代理模式.S1_静态代理;

public class Y3_经纪人 implements Y1_演员 {

    private Y1_演员 演员;

    public Y3_经纪人(Y1_演员 演员) {
        this.演员 = 演员;
    }

    /**
     * 这种修改会对原来的业务产生影响。
     */
    @Override
    public void 演戏(int 收钱) {
        if (收钱 < 50) {
            System.out.println("经纪人：钱太少，不演。");
            return;
        }
        演员.演戏(收钱);
    }

}

package Z_0001_设计模式.D_08_代理模式.S1_静态代理;

import Z_utils.输出;

public class Y2_成龙大哥 implements Y1_演员 {

    @Override
    public void 演戏(int 收钱) {
        输出.当前方法全名("收钱：" + 收钱 + "！ 干活！");
    }

}

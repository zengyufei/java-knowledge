package D_04_建造者模式.S2_建造者模式;

import D_04_建造者模式.S1_需求场景.H1_墙;
import D_04_建造者模式.S1_需求场景.H1_屋顶;
import D_04_建造者模式.S1_需求场景.H1_床;
import D_04_建造者模式.S1_需求场景.H1_门;

public class N3_建筑工人 extends N2_组装 {

    private N1_房子 房子 = new N1_房子();

    @Override
    public void 组装床() {
        房子.床 = new H1_床();
    }

    @Override
    public void 组装门() {
        房子.门 = new H1_门();
    }

    @Override
    public void 组装墙() {
        房子.墙 = new H1_墙();
    }

    @Override
    public void 组装屋顶() {
        房子.屋顶 = new H1_屋顶();
    }

    @Override
    public N1_房子 交付房子() {
        if (房子.床 != null) {
            System.out.println("床组装好了！");
        }
        if (房子.门 != null) {
            System.out.println("门组装好了！");
        }
        if (房子.墙 != null) {
            System.out.println("墙组装好了！");
        }
        if (房子.屋顶 != null) {
            System.out.println("屋顶组装好了！");
        }
        return 房子;
    }
}

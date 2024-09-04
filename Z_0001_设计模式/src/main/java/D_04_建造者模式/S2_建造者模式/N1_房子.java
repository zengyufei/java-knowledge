package D_04_建造者模式.S2_建造者模式;

import D_04_建造者模式.S1_需求场景.H1_墙;
import D_04_建造者模式.S1_需求场景.H1_屋顶;
import D_04_建造者模式.S1_需求场景.H1_床;
import D_04_建造者模式.S1_需求场景.H1_门;

public class N1_房子 {

    public H1_床 床;
    public H1_门 门;
    public H1_墙 墙;
    public H1_屋顶 屋顶;

    public void 入住() {
        System.out.println("恭喜业主入住！");
    }

}

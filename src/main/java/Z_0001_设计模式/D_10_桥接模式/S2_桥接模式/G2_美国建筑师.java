package Z_0001_设计模式.D_10_桥接模式.S2_桥接模式;

import Z_0001_设计模式.D_10_桥接模式.S1_需求场景.J1_建筑师;
import Z_utils.输出;

public class G2_美国建筑师 implements J1_建筑师 {

    @Override
    public void 被聘请来造桥() {
        输出.当前方法全名();
    }

}

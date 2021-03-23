package Z_0001_设计模式.D_10_桥接模式.S1_需求场景;

import Z_utils.输出;

public class J2_中国建筑师建索桥 implements J1_建筑师 {

    @Override
    public void 被聘请来造桥() {
        输出.当前方法全名();
        J3_索桥 中国索桥 = new J3_索桥();
        中国索桥.被建造();
    }

}

package D_10_桥接模式.S1_需求场景;

import Z_utils.输出;

public class J2_美国建筑师建梁桥 implements J1_建筑师 {

    @Override
    public void 被聘请来造桥() {
        输出.当前方法全名();
        J3_梁桥 美国桥 = new J3_梁桥();
        美国桥.被建造();
    }

}

package Z_0001_设计模式.D_10_桥接模式.S1_需求场景;

import Z_utils.Console;

public class J2_中国建筑师建拱桥 implements J1_建筑师 {

    @Override
    public void 被聘请来造桥() {
        Console.getThisMethodFullName();
        J3_拱桥 中国拱桥 = new J3_拱桥();
        中国拱桥.被建造();
    }

}

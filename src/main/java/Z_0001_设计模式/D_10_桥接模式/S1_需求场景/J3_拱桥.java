package Z_0001_设计模式.D_10_桥接模式.S1_需求场景;

import Z_utils.Console;

public class J3_拱桥 implements J1_桥 {

    @Override
    public void 被建造() {
        Console.getThisMethodFullName();
    }

}

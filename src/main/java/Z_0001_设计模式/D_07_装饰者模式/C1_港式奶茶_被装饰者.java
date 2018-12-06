package Z_0001_设计模式.D_07_装饰者模式;

import Z_utils.Console;

/**
 * 被装饰者
 */
public class C1_港式奶茶_被装饰者 implements C1_奶茶{

    @Override
    public void 原料() {
        Console.getThisMethodName("港式奶茶");
    }

    @Override
    public void 价格() {
        Console.getThisMethodName("10 元");
    }
}

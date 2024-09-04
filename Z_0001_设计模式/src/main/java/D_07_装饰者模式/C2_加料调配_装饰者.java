package D_07_装饰者模式;

/**
 * 装饰者
 */
public abstract class C2_加料调配_装饰者 implements C1_奶茶{

    protected C1_奶茶 奶茶;

    public C2_加料调配_装饰者(C1_奶茶 奶茶) {
        this.奶茶 = 奶茶;
    }
}

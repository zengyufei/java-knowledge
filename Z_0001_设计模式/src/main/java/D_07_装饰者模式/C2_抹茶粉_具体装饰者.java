package D_07_装饰者模式;

/**
 * 具体装饰者
 */
public class C2_抹茶粉_具体装饰者 extends C2_加料调配_装饰者 {

    public C2_抹茶粉_具体装饰者(C1_奶茶 奶茶) {
        super(奶茶);
    }

    @Override
    public void 原料() {
        奶茶.原料();
        System.out.println(" + 抹茶粉");
    }

    @Override
    public void 价格() {
        奶茶.价格();
        System.out.println(" + 5 元");
    }
}

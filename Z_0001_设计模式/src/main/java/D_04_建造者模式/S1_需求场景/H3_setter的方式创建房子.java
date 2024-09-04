package D_04_建造者模式.S1_需求场景;

/**
 * 典型的 java bean 的形式，解决了构造函数的不足，但是如果我们的私有属性增加到二十，那么我们每次创建的时候就需要写二十行 setter 代码，累不累？
 */
public class H3_setter的方式创建房子 {

    public H1_床 床;
    public H1_门 门;
    public H1_墙 墙;
    public H1_屋顶 屋顶;

    public void set床(H1_床 床) {
        this.床 = 床;
    }

    public void set门(H1_门 门) {
        this.门 = 门;
    }

    public void set墙(H1_墙 墙) {
        this.墙 = 墙;
    }

    public void set屋顶(H1_屋顶 屋顶) {
        this.屋顶 = 屋顶;
    }

    public H1_床 get床() {
        return 床;
    }

    public H1_门 get门() {
        return 门;
    }

    public H1_墙 get墙() {
        return 墙;
    }

    public H1_屋顶 get屋顶() {
        return 屋顶;
    }
}

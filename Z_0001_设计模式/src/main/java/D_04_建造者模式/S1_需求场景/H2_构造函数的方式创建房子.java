package D_04_建造者模式.S1_需求场景;

/**
 * 使用构造函数的方式
 * 这种方式会使此类中存在很多构造方法，而且不能满足所有的组合，客户端在制作房屋的时候，比较不适用。
 */
public class H2_构造函数的方式创建房子 {

    public H1_床 床;
    public H1_门 门;
    public H1_墙 墙;
    public H1_屋顶 屋顶;

    public H2_构造函数的方式创建房子() {
    }

    public H2_构造函数的方式创建房子(H1_床 床) {
        this.床 = 床;
    }

    public H2_构造函数的方式创建房子(H1_床 床, H1_门 门) {
        this.床 = 床;
        this.门 = 门;
    }

    public H2_构造函数的方式创建房子(H1_床 床, H1_门 门, H1_墙 墙) {
        this.床 = 床;
        this.门 = 门;
        this.墙 = 墙;
    }

    public H2_构造函数的方式创建房子(H1_床 床, H1_门 门, H1_墙 墙, H1_屋顶 屋顶) {
        this.床 = 床;
        this.门 = 门;
        this.墙 = 墙;
        this.屋顶 = 屋顶;
    }
}

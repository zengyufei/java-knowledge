package Z_0001_设计模式.D_04_建造者模式.S2_建造者模式;

public abstract class N2_组装 {

    public abstract void 组装床();

    public abstract void 组装门();

    public abstract void 组装墙();

    public abstract void 组装屋顶();

    public abstract N1_房子 交付房子();

}

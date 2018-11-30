package Z_0001_设计模式.D_01_工厂方法模式.S1_需求场景;

/**
 * 场景：我们是一家披萨供应商，制作披萨需要水和面粉的，所以我们的业务代码应该如下
 */
public class X1_披萨实体类 {

    public void 加水() {
        System.out.println(new Exception().getStackTrace()[0].getMethodName());
    }

    public void 加面粉() {
        System.out.println(new Exception().getStackTrace()[0].getMethodName());
    }

}

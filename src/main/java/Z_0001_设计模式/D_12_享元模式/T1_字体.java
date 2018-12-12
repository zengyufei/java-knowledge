package Z_0001_设计模式.D_12_享元模式;

/**
 * 描述：相同参数当多次读取时，反对同一对象，这就是享元模式的核心思想。复用对象，节省内存开销和对象创建时间。
 * @author zengyufei
 */
public interface T1_字体 {

    String 创建字体();
}

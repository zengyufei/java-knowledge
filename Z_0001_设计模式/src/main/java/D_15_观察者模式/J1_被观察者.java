package D_15_观察者模式;

/**
 * 描述：用于管理监听的观察者，实现数据更新时对他们的通知
 * @author zengyufei
 */
public abstract class J1_被观察者 {

    abstract void 注册观察者(J2_观察者 观察者);

    abstract void 通知观察者(String 动作);

    abstract void 移除观察者();

}

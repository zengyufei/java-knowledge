package Z_0001_设计模式.D_15_观察者模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * @author zengyufei
 */
public class J3_学生 extends J1_被观察者 {

    List<J2_观察者> 一群老师 = new ArrayList<>();

    @Override
    void 注册观察者(J2_观察者 观察者) {
        一群老师.add(观察者);
    }

    @Override
    void 通知观察者(String 动作) {
        for (J2_观察者 老师 : 一群老师) {
            老师.做出反应(动作);
        }
    }

    @Override
    void 移除观察者() {
        一群老师.remove(一群老师.size() - 1);
    }

    public void set动作(String 动作) {
        System.out.println("学生：" + 动作);
        通知观察者(动作);
    }
}

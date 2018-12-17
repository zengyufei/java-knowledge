package Z_0001_设计模式.D_16_迭代器模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * @author zengyufei
 */
public class O2_列表<T> implements O1_迭代器接口<T> {

    List<T> 数据 = new ArrayList<>();
    int 下标 = 0;

    @Override
    public boolean 是否有下一个() {
        return 下标 < 数据.size();
    }

    @Override
    public T 下一个() {
        return 数据.get(下标++);
    }
}

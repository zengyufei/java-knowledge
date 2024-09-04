package D_12_享元模式;

import cn.hutool.core.map.MapUtil;

import java.util.Map;
import java.util.Objects;

/**
 * 描述：字体生成类
 * @author zengyufei
 */
public class T3_字体工厂类 {

    private static Map<String, T1_字体> 字体缓存 = MapUtil.newHashMap();

    public static T1_字体 获取新宋体(String 什么字体) {
        T1_字体 字体 = 字体缓存.get(什么字体);
        if (Objects.isNull(字体)) {
            字体 = new T2_新宋体字体();
            字体缓存.put(什么字体, 字体);
        }
        return 字体;
    }

}

package Z_0001_设计模式.D_11_组合模式;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

public class Z1_树 {

    String 唯一标识;
    String 树节点名称;
    List<Z1_树> 子树;

    public String get唯一标识() {
        return 唯一标识;
    }

    public void set唯一标识(String 唯一标识) {
        this.唯一标识 = 唯一标识;
    }

    public String get树节点名称() {
        return 树节点名称;
    }

    public void set树节点名称(String 树节点名称) {
        this.树节点名称 = 树节点名称;
    }

    public List<Z1_树> get子树() {
        return 子树;
    }

    public void set子树(List<Z1_树> 子树) {
        this.子树 = 子树;
    }

    public void 展示树结构整体() {
        System.out.println("-- " + this.树节点名称);
        if (CollUtil.isNotEmpty(this.子树)) {
            for (Z1_树 z1_树 : this.子树) {
                System.out.println("---- " +z1_树.树节点名称);
                if (CollUtil.isNotEmpty(z1_树.子树)) {
                    showChild(z1_树, "----");
                }
            }
        }
    }

    private void showChild(Z1_树 树, String 缩进符) {
        if (CollUtil.isNotEmpty(树.子树)) {
            for (Z1_树 子树 : 树.子树) {
                System.out.println(缩进符 + "-- " +子树.树节点名称);
                if (CollUtil.isNotEmpty(子树.子树)) {
                    showChild(子树, 缩进符);
                }
            }
        }
    }
}

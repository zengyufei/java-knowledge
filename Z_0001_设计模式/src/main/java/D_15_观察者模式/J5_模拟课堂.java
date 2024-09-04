package D_15_观察者模式;

/**
 * 描述：
 * @author zengyufei
 */
public class J5_模拟课堂 {

    public static void main(String[] args) {
        J3_学生 小明 = new J3_学生();
        J4_老师 张老师 = new J4_老师();
        小明.注册观察者(张老师);
        小明.set动作("挖鼻孔");
        小明.set动作("认真上课");
        小明.set动作("玩王者荣耀");
        小明.set动作("睡觉");
    }

}

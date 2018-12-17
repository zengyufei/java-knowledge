package Z_0001_设计模式.D_15_观察者模式;

/**
 * 描述：
 * @author zengyufei
 */
public class J4_老师 implements J2_观察者 {

    @Override
    public void 做出反应(String 学生的行为) {
        switch (学生的行为) {
            case "挖鼻孔":
                System.out.println("老师: 当没看到...");
                break;
            case "睡觉":
                System.out.println("老师: 过去敲醒！");
                break;
            case "聊天":
                System.out.println("老师: 点名批评！");
                break;
            case "玩王者荣耀":
                System.out.println("老师: 没收手机！");
                break;
            default:
                System.out.println("老师: 好学生!");
        }
    }

}

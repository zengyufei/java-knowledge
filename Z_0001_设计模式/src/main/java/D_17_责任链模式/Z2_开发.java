package D_17_责任链模式;

/**
 * 描述：
 * @author zengyufei
 */
public class Z2_开发 extends Z1_锅 {

    String 开发的姓名;
    Z2_开发 下一个开发;

    public Z2_开发(String 开发的姓名) {
        this.开发的姓名 = 开发的姓名;
    }

    @Override
    void 反应() {
        if (下一个开发 == null) {
            System.out.println(开发的姓名+ "：??? 是你的锅吗？");
            System.out.println(开发的姓名 + "：人呢？？");
            return;
        }
        System.out.println(开发的姓名 + "，是你的锅吗？");
        if (开发的姓名.equals("C")) {
            System.out.println(开发的姓名 + "：是！");
        }else{
            System.out.println(开发的姓名 + "：不是。你问一下 " + 下一个开发.开发的姓名 + " 吧。");
            下一个开发.反应();
        }
    }

}

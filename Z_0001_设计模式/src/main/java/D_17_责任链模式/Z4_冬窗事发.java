package D_17_责任链模式;

/**
 * 描述：
 * @author zengyufei
 */
public class Z4_冬窗事发 {

    public static void main(String[] args) {
        Z2_开发 开发A = new Z2_开发("A");
        Z2_开发 开发B = new Z2_开发("B");
        Z2_开发 开发C = new Z2_开发("C");
        Z2_开发 开发D = new Z2_开发("D");
        Z2_开发 开发E = new Z2_开发("E");

        开发A.下一个开发 = 开发B;
        开发B.下一个开发 = 开发C;
        开发C.下一个开发 = 开发D;
        开发D.下一个开发 = 开发E;

        Z3_误删生产库的锅的开发.询问是不是你的锅(开发A);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>  分割线  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        Z3_误删生产库的锅的开发.询问是不是你的锅(开发D);

    }

}

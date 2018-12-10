package Z_0001_设计模式.D_10_桥接模式.S1_需求场景;

/**
 * 如何理解桥接模式：
 * -- 中国桥有【索桥、梁桥、拱桥】，其中每种桥又分为中国人造的和美国人造的，排列组合起来，共 3 种不同国家造的中国桥。
 * -- 同样美国桥也有【索桥、梁桥、拱桥】，区分国家，也排列组合出 3 种不同的美国桥。
 * -- 如果是如此，需要创建 9 个类来组合不同的国家不同的桥，每新增一个国家类别，就必须 +3 个类来代表桥的种类。
 * -- 桥接模式是为了简化上面创建桥的方式：抽象桥梁种类和国家类别，自由组合出不同的桥。
 * -- 使用桥接模式，只需要 3（桥的种类） + 2 （国家类别），后续新增国家类别，也只是 +1 就能达到目的。
 */
public class J4_建造项目工程 {

    public static void main(String[] args) {
        J1_建筑师 索桥中国建筑师 = new J2_中国建筑师建索桥();
        J1_建筑师 梁桥中国建筑师 = new J2_中国建筑师建梁桥();
        J1_建筑师 拱桥中国建筑师 = new J2_中国建筑师建拱桥();

        J1_建筑师 索桥美国建筑师 = new J2_美国建筑师建索桥();
        J1_建筑师 梁桥美国建筑师 = new J2_美国建筑师建梁桥();
        J1_建筑师 拱桥美国建筑师 = new J2_美国建筑师建拱桥();

        索桥中国建筑师.被聘请来造桥();
        梁桥中国建筑师.被聘请来造桥();
        拱桥中国建筑师.被聘请来造桥();

        System.out.println("=========================== 分割线 =============================");

        索桥美国建筑师.被聘请来造桥();
        梁桥美国建筑师.被聘请来造桥();
        拱桥美国建筑师.被聘请来造桥();

        System.out.println("=========================== 分割线 =============================");
    }

}

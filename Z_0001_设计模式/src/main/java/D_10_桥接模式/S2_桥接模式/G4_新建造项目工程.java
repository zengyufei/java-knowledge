package D_10_桥接模式.S2_桥接模式;

import D_10_桥接模式.S1_需求场景.J1_建筑师;

/**
 * 如何理解桥接模式：
 * -- 中国桥有【索桥、梁桥、拱桥】，其中每种桥又分为中国人造的和美国人造的，排列组合起来，共 3 种不同国家造的中国桥。
 * -- 同样美国桥也有【索桥、梁桥、拱桥】，区分国家，也排列组合出 3 种不同的美国桥。
 * -- 如果是如此，需要创建 9 个类来组合不同的国家不同的桥，每新增一个国家类别，就必须 +3 个类来代表桥的种类。
 * -- 桥接模式是为了简化上面创建桥的方式：抽象桥梁种类和国家类别，自由组合出不同的桥。
 * -- 使用桥接模式，只需要 3（桥的种类） + 2 （国家类别），后续新增国家类别，也只是 +1 就能达到目的。
 */
public class G4_新建造项目工程 {

    public static void main(String[] args) {

        J1_建筑师 中国建筑师 = new G2_中国建筑师();
        J1_建筑师 美国建筑师 = new G2_美国建筑师();
        G1_桥 拱桥 = new G3_拱桥();
        G1_桥 梁桥 = new G3_梁桥();
        G1_桥 索桥 = new G3_索桥();

        拱桥.被建造(中国建筑师);
        梁桥.被建造(中国建筑师);
        索桥.被建造(中国建筑师);

        拱桥.被建造(美国建筑师);
        梁桥.被建造(美国建筑师);
        索桥.被建造(美国建筑师);


    }

}

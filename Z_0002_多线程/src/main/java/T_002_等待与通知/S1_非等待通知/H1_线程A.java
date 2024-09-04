package T_002_等待与通知.S1_非等待通知;

import java.util.ArrayList;
import java.util.List;

public class H1_线程A extends Thread {

    public final static List list = new ArrayList<>();

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println("添加了" + (i + 1) + "个元素");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

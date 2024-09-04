package D_05_原型模式.S2_原型模式;

import java.io.*;

public class Y3_手机 implements Cloneable, Serializable {

    private static final long serialVersionUID = 1527377858462124822L;

    public String 颜色;
    public String 价格;
    public Y3_账号 账号;

    @Override
    public String toString() {
        return "Y1_手机{" +
                "颜色='" + 颜色 + '\'' +
                ", 价格='" + 价格 + '\'' +
                ", 账号=" + 账号 +
                '}';
    }

    @Override
    protected Object clone() {
        Y3_手机 克隆手机 = null;
        try (
                // 将对象写到流里
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeObject(this);
            try (
                    // 从流里读回来
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
            ) {
                克隆手机 = (Y3_手机) objectInputStream.readObject();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 克隆手机;
    }
}

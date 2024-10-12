package M_11_Direct交换机模式;

public interface Q0_常量 {

    String HOST = "10.0.0.4";

    int 端口 = 5672;

    String 账号 = "root";

    String 密码 = "root";

    String 交换机名称 = "M_11";
    String 队列名称 = "M_11_exchange_direct";
    String[] 路由键数组 = {"info", "error"};
}

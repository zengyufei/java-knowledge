package M_12_Topic交换机模式;

public interface R0_常量 {

    String HOST = "10.0.0.4";

    int 端口 = 5672;

    String 账号 = "root";

    String 密码 = "root";

    String 交换机名称 = "M_12";
    String 队列名称 = "M_12_exchange_topic";
    String[] 路由键数组 = {"*.*.rabbit", "lazy.#", "*.orange.*"};
}

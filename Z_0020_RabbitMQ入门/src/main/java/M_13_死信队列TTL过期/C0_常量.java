package M_13_死信队列TTL过期;

public interface C0_常量 {

    String HOST = "10.0.0.4";

    int 端口 = 5672;

    String 账号 = "root";

    String 密码 = "root";

    String 普通交换机名称 = "M_13_normal_ttl";
    String 死信交换机名称 = "M_13_dead_ttl";

    String 普通队列名称 = "M_13_normal_queue_ttl";
    String 死信队列名称 = "M_13_dead_queue_ttl";

    String 普通路由键 = "normal_ttl";
    String 死信路由键 = "dead_ttl";
}

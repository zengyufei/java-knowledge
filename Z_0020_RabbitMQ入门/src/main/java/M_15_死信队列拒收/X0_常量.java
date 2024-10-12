package M_15_死信队列拒收;

public interface X0_常量 {

    String HOST = "10.0.0.4";

    int 端口 = 5672;

    String 账号 = "root";

    String 密码 = "root";

    String 普通交换机名称 = "M_14_normal_reject";
    String 死信交换机名称 = "M_14_dead_reject";

    String 普通队列名称 = "M_14_normal_queue_reject";
    String 死信队列名称 = "M_14_dead_queue_reject";

    String 普通路由键 = "normal_reject";
    String 死信路由键 = "dead_reject";
}

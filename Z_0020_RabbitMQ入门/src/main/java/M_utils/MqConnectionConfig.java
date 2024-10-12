package M_utils;

import lombok.Data;

@Data
public class MqConnectionConfig {

    private final static int DEFAULT_MAX_CONNECTION_USING_COUNT = 300; // 默认最大连接可访问次数

    private String host;

    private String username;

    private String password;

    private int port;

}

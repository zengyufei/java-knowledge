package M_utils;

import com.rabbitmq.client.BuiltinExchangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder(builderMethodName = "of")
public class MqConfig {

    private String title;

    private String type;

    private String changeName;

    private String queueName;

    private String routingKey;

    private String expiration;

    private Boolean durable;

    private BuiltinExchangeType exchangeType;

    private Long ttl;

    private Long max;

    private DeadConfig deadConfig;

    private Boolean isAutoClose;

    private Boolean isDelay;

    private Long delayTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder(builderMethodName = "of")
public  static class DeadConfig {

    private String changeName;

    private String queueName;

    private String routingKey;

}

}

package Z_0019_Netty入门IO通讯.S17_Netty实现一对一单聊.Y4_应用层;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Y12_会话 {
    String 用户id;
    String 用户名;
}

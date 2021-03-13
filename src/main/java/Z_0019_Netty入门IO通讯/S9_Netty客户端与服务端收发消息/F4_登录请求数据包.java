package Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息;

import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F1_抽象数据包;
import Z_0019_Netty入门IO通讯.S9_Netty客户端与服务端收发消息.F0_协议.F1_指令;

/**
 * 描述：
 * @author zengyufei
 */
public class F4_登录请求数据包 extends F1_抽象数据包 {
    
    public String 账号;
    public String 密码;
    public String 姓名;
    
    @Override
    public Byte 指令() {
        return F1_指令.登录请求;
    }
    
    // json 序列化需要 getter/setter
    
    public String get账号() {
        return 账号;
    }
    
    public void set账号(String 账号) {
        this.账号 = 账号;
    }
    
    public String get密码() {
        return 密码;
    }
    
    public void set密码(String 密码) {
        this.密码 = 密码;
    }
    
    public String get姓名() {
        return 姓名;
    }
    
    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }
}

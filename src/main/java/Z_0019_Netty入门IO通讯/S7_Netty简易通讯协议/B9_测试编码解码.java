package Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议;

import Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B2_接口层.B3_抽象数据包;
import Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B3_实现层.B5_具体数据包;
import Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B4_应用层.B7_编码;
import Z_0019_Netty入门IO通讯.S7_Netty简易通讯协议.B4_应用层.B8_解码;
import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;

/**
 * 描述：
 *
 * @author zengyufei
 */
public class B9_测试编码解码 {

    public static void main(String[] args) {
        B5_具体数据包 数据包 = new B5_具体数据包();
        数据包.set用户唯一标识("admin");
        数据包.set用户名("管理员");
        数据包.set密码("123456");

        ByteBuf 编码后二进制流 = B7_编码.编码(数据包);
        B3_抽象数据包 解码后抽象数据包 = B8_解码.解码(编码后二进制流);

        B5_具体数据包 解码后数据包 = (B5_具体数据包) 解码后抽象数据包;
        Console.log(JSONObject.toJSONString(解码后数据包));
    }

}

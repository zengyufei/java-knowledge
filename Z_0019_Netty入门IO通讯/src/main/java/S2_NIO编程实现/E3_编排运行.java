package S2_NIO编程实现;

import java.io.IOException;

public class E3_编排运行 {
    public static void main(String[] args) throws IOException {
        E1_服务端.运行();
        E2_客户端.运行();
    }
}

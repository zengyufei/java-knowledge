package K005_Netty_简单RPC实现;


public class Q3_服务接口服务端实现 implements Q3_服务接口 {
    @Override
    public Q2_返回参 计算加法(final Q2_请求参 入参) {
        final Q2_返回参 出参 = new Q2_返回参(false, null);
        final int 计算结果 = 入参.get参数一() + 入参.get参数二();
        出参.set成功状态(true);
        出参.set计算结果(计算结果);
        return 出参;
    }
}

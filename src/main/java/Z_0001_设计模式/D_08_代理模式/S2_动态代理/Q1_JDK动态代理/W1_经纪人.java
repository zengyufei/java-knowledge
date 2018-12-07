package Z_0001_设计模式.D_08_代理模式.S2_动态代理.Q1_JDK动态代理;

import cn.hutool.core.convert.Convert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class W1_经纪人 implements InvocationHandler {

    private Object 演员;

    W1_经纪人(Object 演员) {
        this.演员 = 演员;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        int 钱 = Convert.toInt(args[0], 0);
        if (钱 < 50) {
            System.out.println("经纪人：钱太少，不演。");
            return result;
        }
        return method.invoke(演员, args);
    }

    public static Object 代理(Object 演员) {
        Class<?> cls = 演员.getClass();
        return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(),
                new W1_经纪人(演员));
    }

}

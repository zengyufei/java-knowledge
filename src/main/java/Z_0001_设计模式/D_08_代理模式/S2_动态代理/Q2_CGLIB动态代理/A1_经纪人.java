package Z_0001_设计模式.D_08_代理模式.S2_动态代理.Q2_CGLIB动态代理;

import cn.hutool.core.convert.Convert;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class A1_经纪人  implements MethodInterceptor {

    private Object 演员;

    A1_经纪人(Object 演员) {
        this.演员 = 演员;
    }

    public Object 代理() {
        Enhancer enhancer = new Enhancer();
        // 通过字节码技术动态创建子类实例
        enhancer.setSuperclass(演员.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;
        int 钱 = Convert.toInt(args[0], 0);
        if (钱 < 50) {
            System.out.println("经纪人：钱太少，不演。");
            return result;
        }
        // 通过代理类调用父类中的方法
        return proxy.invokeSuper(obj, args);
    }


}

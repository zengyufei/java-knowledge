package com.zyf.manual.bean.config;

import cn.hutool.core.util.StrUtil;
import com.zyf.manual.bean.entity.AutoBean;
import com.zyf.manual.bean.entity.AutoDIBean;
import com.zyf.manual.bean.entity.AutoFacDIBean;
import com.zyf.manual.bean.entity.OriginBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 16:14 18/10/13.
 */
@Slf4j
@Configuration
public class AutoBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    /**
     * 这个接口的作用是在Spring上下文的注册Bean定义的逻辑都跑完后，但是所有的Bean都还没真正实例化之前调用。
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 注册Bean定义，容器根据定义返回bean

        //构造bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AutoBean.class);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        //注册bean定义
        registry.registerBeanDefinition(StrUtil.lowerFirst(AutoBean.class.getSimpleName()), beanDefinition);


        // AutoDIBean 的注入方式
        beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AutoDIBean.class);
        beanDefinitionBuilder.addConstructorArgValue("自动注入依赖Bean");
        beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        registry.registerBeanDefinition(StrUtil.lowerFirst(AutoDIBean.class.getSimpleName()), beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        // 注册Bean实例，使用supply接口, 可以创建一个实例，并主动注入一些依赖的Bean；当这个实例对象是通过动态代理这种框架生成时，就比较有用了

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AutoFacDIBean.class, () -> {
            AutoFacDIBean autoFacDIBean = new AutoFacDIBean(StrUtil.lowerFirst(AutoFacDIBean.class.getSimpleName()));
            autoFacDIBean.setAutoBean(factory.getBean(StrUtil.lowerFirst(AutoBean.class.getSimpleName()), AutoBean.class));
            autoFacDIBean.setOriginBean(factory.getBean(StrUtil.lowerFirst(OriginBean.class.getSimpleName()), OriginBean.class));
            return autoFacDIBean;
        });
        BeanDefinition beanDefinition = builder.getRawBeanDefinition();
        ((DefaultListableBeanFactory) factory).registerBeanDefinition(StrUtil.lowerFirst(AutoFacDIBean.class.getSimpleName()), beanDefinition);
    }
}

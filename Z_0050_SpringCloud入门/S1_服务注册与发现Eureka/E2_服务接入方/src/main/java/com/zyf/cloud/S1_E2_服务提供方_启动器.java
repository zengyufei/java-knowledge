package com.zyf.cloud;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*
 * @EnableDiscoveryClient 注解是基于 spring-cloud-commons 依赖，相当于一个公共的服务发现；
 * @EnableEurekaClient 注解是基于 spring-cloud-netflix 依赖，只能为 eureka 作用；
 * 其实用更简单的话来说，就是如果选用的注册中心是 eureka，那么就推荐 @EnableEurekaClient，如果是其他的注册中心，那么推荐使用 @EnableDiscoveryClient。
 */
//@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class S1_E2_服务提供方_启动器 implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            beanFactory.getBeanDefinition(beanName).setLazyInit(true);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(S1_E2_服务提供方_启动器.class, args);
    }

}


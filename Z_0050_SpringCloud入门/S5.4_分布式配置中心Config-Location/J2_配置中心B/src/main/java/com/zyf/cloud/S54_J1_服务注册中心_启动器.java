package com.zyf.cloud;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.io.IOException;

@EnableConfigServer
@SpringBootApplication
public class S54_J1_服务注册中心_启动器 implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            beanFactory.getBeanDefinition(beanName).setLazyInit(true);
        }
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(S54_J1_服务注册中心_启动器.class, args);
    }

}


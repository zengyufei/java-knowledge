package com.zyf.cloud;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileWriter;
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
        writeFile();
        SpringApplication.run(S54_J1_服务注册中心_启动器.class, args);
    }

    private static void writeFile() throws IOException {
        String dirPath = "d:\\java";
        File file = new File(dirPath);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
            if (!mkdir) {
                throw new IOException();
            }
        } else {
            String defaultPath = dirPath + "\\config-client.yml";
            String devPath = dirPath + "\\config-client-dev.yml";
            FileCopyUtils.copy("info:\n" +
                    "  profile: default-location", new FileWriter(defaultPath));
            FileCopyUtils.copy("info:\n" +
                    "  profile: dev-location", new FileWriter(devPath));
        }
    }

}


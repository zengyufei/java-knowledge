package com.zyf.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
 * @EnableDiscoveryClient 注解是基于 spring-cloud-commons 依赖，相当于一个公共的服务发现；
 * @EnableEurekaClient 注解是基于 spring-cloud-netflix 依赖，只能为 eureka 作用；
 * 其实用更简单的话来说，就是如果选用的注册中心是 eureka，那么就推荐 @EnableEurekaClient，如果是其他的注册中心，那么推荐使用 @EnableDiscoveryClient。
 */
//@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class S2_R3_服务消费方_启动器 implements CommandLineRunner {

    /**
     * 给 RestTemplate 开外挂。开启均衡负载能力。
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(S2_R3_服务消费方_启动器.class, args);
    }

    /**
     * 启动 springboot 后调用的方法
     */
    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(5000);
        String[] urls = new String[]{"eureka-client-a", "eureka-client-b"};
        for (String clientName : urls) {
            Integer result = restTemplate.getForEntity("http://" + clientName + "/add?a=1&b=2", Integer.class).getBody();
            System.out.println("=======================================");
            System.out.println(clientName + " : " + result);
            System.out.println("=======================================");
        }
    }
}


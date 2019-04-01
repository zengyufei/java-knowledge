package com.zyf.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class S3_F1_服务注册中心_lite_启动器 {

	public static void main(String[] args) {
		SpringApplication.run(S3_F1_服务注册中心_lite_启动器.class, args);
	}

}


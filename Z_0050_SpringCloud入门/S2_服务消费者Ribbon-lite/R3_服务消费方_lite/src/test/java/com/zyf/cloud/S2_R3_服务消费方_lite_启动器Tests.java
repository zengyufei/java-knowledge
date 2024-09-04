package com.zyf.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S2_R3_服务消费方_lite_启动器Tests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        String[] urls = new String[]{"eureka-client-lite"};
        for (String clientName : urls) {
            Integer result = restTemplate.getForEntity("http://" + clientName + "/add?a=1&b=2", Integer.class).getBody();
            Assert.isTrue(result == 3, clientName + "服务调用失败！");
        }
    }

}


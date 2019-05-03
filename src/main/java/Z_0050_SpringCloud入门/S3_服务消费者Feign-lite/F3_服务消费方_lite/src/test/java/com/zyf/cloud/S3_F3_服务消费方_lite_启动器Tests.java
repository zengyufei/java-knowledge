package com.zyf.cloud;

import com.zyf.cloud.service.消费服务;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S3_F3_服务消费方_lite_启动器Tests {

    @Autowired
    消费服务 消费;

    @Test
    public void contextLoads() {
        Integer result = 消费.add(1, 2);
        Assert.isTrue(result == 3, "服务调用失败！");
    }

}


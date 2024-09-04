package com.zyf.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述： 提供计算功能的微服务模块。
 * 我们实现一个 RESTful API，通过传入两个参数 a 和 b，最后返回 a + b 的结果。
 * @author zengyufei
 */
@RestController
public class 提供服务_lite {

    @RequestMapping("/add")
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

}

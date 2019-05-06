package com.zyf.cloud.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述： 提供计算功能的微服务模块。
 * 我们实现一个 RESTful API，通过传入两个参数 a 和 b，最后返回 a + b 的结果。
 * @author zengyufei
 */
@RestController
public class 提供服务 {

    @RequestMapping("/add")
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart(value = "file") MultipartFile file) {
        return file.getOriginalFilename();
    }

}

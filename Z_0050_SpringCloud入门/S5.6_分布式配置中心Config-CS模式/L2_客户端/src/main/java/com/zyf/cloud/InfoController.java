package com.zyf.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${info.profile}")
    String info;

    @RequestMapping(value = "/info")
    public String info() {
        return info;
    }

}
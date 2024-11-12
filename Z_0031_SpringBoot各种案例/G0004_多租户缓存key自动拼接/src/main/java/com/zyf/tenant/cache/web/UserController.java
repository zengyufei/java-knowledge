package com.zyf.tenant.cache.web;

import cn.hutool.core.map.MapUtil;
import com.zyf.tenant.cache.entity.UserEntity;
import com.zyf.tenant.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * UserController
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("selectList")
    public Object selectList() {
        return success(service.selectList());
    }

    @GetMapping("getOne")
    public Object getOne(Integer id) {
        return success(service.getOne(id));
    }


    public Map<String, Object> success(Object obj) {
        Map<String, Object> result = success();
        result.put("data", obj);
        return result;
    }

    public Map<String, Object> success() {
        Map<String, Object> result = MapUtil.newHashMap();
        result.put("code", 200);
        result.put("msg", "success");
        return result;
    }

}

package com.zyf.tenant.cache.web;

import cn.hutool.core.map.MapUtil;
import com.zyf.tenant.cache.entity.UserEntity;
import com.zyf.tenant.cache.service.CommonNotService;
import com.zyf.tenant.cache.service.CommonService;
import com.zyf.tenant.cache.tenant.TenantContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * CommonController
 */
@RestController
@RequestMapping("/commonNot")
public class CommonNotAppendTenantIdController {

    @Autowired
    private CommonNotService service;

    @GetMapping("selectList")
    public Object selectList() {
        final List<UserEntity> userEntities = TenantContextHolder.notAppendTenantKey(() -> service.selectList());
        return success(userEntities);
    }

    @GetMapping("getOne")
    public Object getOne(Integer id) {
        final UserEntity userEntity = TenantContextHolder.notAppendTenantKey(() -> service.getOne(id));
        return success(userEntity);
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

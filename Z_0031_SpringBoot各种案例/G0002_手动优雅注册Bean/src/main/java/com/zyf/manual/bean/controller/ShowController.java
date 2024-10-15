package com.zyf.manual.bean.controller;

import cn.hutool.json.JSONUtil;
import com.zyf.manual.bean.entity.AnoAutoOriginBean;
import com.zyf.manual.bean.entity.AutoBean;
import com.zyf.manual.bean.entity.AutoDIBean;
import com.zyf.manual.bean.entity.AutoFacDIBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 15:11 18/10/13.
 */
@RestController
public class ShowController {

    @Autowired
    private AutoBean autoBean;
    @Autowired
    private AutoDIBean autoDIBean;
    @Autowired
    private AutoFacDIBean autoFacDIBean;
    @Autowired
    private AnoAutoOriginBean anoAutoOriginBean;
    @GetMapping(path = "auto")
    public String autoShow() {
        Map<String, String> result = new HashMap<>(8);
        result.put("autoBean", autoBean == null ? "null" : autoBean.print());
        result.put("manualDIBean", autoDIBean == null ? "null" : autoDIBean.print());
        result.put("autoFacDIBean",autoFacDIBean == null ? "null" : autoFacDIBean.print());
        result.put("anoAutoOriginBean",anoAutoOriginBean.print());
        return JSONUtil.toJsonPrettyStr(result);
    }

}

package com.zyf.manual.bean.controller;

import cn.hutool.json.JSONUtil;
import com.zyf.manual.bean.entity.ManualBean;
import com.zyf.manual.bean.entity.ManualDIBean;
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
    private ManualBean manualBean;
    @Autowired
    private ManualDIBean manualDIBean;

    public ShowController() {
        System.out.println("ShowController init: " + System.currentTimeMillis());
    }

    @GetMapping(path = "show")
    public String show(String msg) {
        Map<String, String> result = new HashMap<>(8);
        result.put("manualBean", manualBean == null ? "null" : manualBean.print(msg));
        result.put("manualDIBean", manualDIBean == null ? "null" : manualDIBean.print(msg));
        return JSONUtil.toJsonPrettyStr(result);
    }

}

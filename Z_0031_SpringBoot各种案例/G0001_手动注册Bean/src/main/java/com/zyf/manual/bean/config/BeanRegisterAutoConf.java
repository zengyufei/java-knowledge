package com.zyf.manual.bean.config;

import cn.hutool.core.util.StrUtil;
import com.zyf.manual.bean.entity.ManualBean;
import com.zyf.manual.bean.entity.ManualDIBean;
import com.zyf.manual.bean.utils.ManualRegistBeanUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 15:07 18/10/13.
 */
@Configuration
public class BeanRegisterAutoConf {

    public BeanRegisterAutoConf(ApplicationContext applicationContext) {
        System.out.println("BeanRegisterAutoConf init: " + System.currentTimeMillis());
        registerManualBean((ConfigurableApplicationContext) applicationContext);
    }

    /**
     * 手动注册自定义地bean
     * @param applicationContext
     */
    private void registerManualBean(ConfigurableApplicationContext applicationContext) {
        // 主动注册一个没什么依赖的Bean
        ManualBean manualBean = ManualRegistBeanUtil.registerBean(applicationContext, StrUtil.lowerFirst(ManualBean.class.getSimpleName()), ManualBean.class);
        manualBean.print("test print manualBean");

        // manualDIBean 内部，依赖由Spring容器创建的OriginBean
        ManualDIBean manualDIBean = ManualRegistBeanUtil.registerBean(applicationContext, StrUtil.lowerFirst(ManualDIBean.class.getSimpleName()),
                ManualDIBean.class, "依赖OriginBean的自定义Bean");
        manualDIBean.print("test print manualDIBean");
    }
}

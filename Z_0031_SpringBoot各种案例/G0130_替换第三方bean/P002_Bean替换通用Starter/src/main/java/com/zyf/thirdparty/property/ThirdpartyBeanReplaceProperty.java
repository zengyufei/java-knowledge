package com.zyf.thirdparty.property;


import com.zyf.thirdparty.entitys.ThirdpartyReplaceBeanHolder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Role;

import java.util.List;


@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConfigurationProperties(prefix = ThirdpartyBeanReplaceProperty.PREFIX)
public class ThirdpartyBeanReplaceProperty {

    public static final String PREFIX = "bean-replace";

    private boolean beanReplace = true;

    private List<ThirdpartyReplaceBeanHolder> replaceBeans;

    public boolean isBeanReplace() {
        return beanReplace;
    }

    public void setBeanReplace(boolean beanReplace) {
        this.beanReplace = beanReplace;
    }

    public List<ThirdpartyReplaceBeanHolder> getReplaceBeans() {
        return replaceBeans;
    }

    public void setReplaceBeans(List<ThirdpartyReplaceBeanHolder> replaceBeans) {
        this.replaceBeans = replaceBeans;
    }
}

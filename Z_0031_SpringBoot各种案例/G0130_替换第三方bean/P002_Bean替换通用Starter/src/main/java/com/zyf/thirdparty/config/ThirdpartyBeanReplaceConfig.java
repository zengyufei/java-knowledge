package com.zyf.thirdparty.config;

import com.zyf.thirdparty.processor.ThirdpartyBeanReplaceBeanPostProcessor;
import com.zyf.thirdparty.property.ThirdpartyBeanReplaceProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(ThirdpartyBeanReplaceProperty.class)
@ConditionalOnProperty(prefix = ThirdpartyBeanReplaceProperty.PREFIX,name = "enable",havingValue = "true",matchIfMissing = true)
public class ThirdpartyBeanReplaceConfig {

    @Bean
    @ConditionalOnMissingBean
    public static ThirdpartyBeanReplaceBeanPostProcessor thirdpartyBeanReplaceBeanPostProcessor(ThirdpartyBeanReplaceProperty thirdpartyBeanReplaceProperty){
        return new ThirdpartyBeanReplaceBeanPostProcessor(thirdpartyBeanReplaceProperty);
    }

}

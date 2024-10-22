package com.zyf.thirdparty.config;

import com.zyf.thirdparty.dao.ThirdpartyDao;
import com.zyf.thirdparty.service.ThirdpartyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ThirdpartyConfig {

    @Bean
    @Primary
    public ThirdpartyDao thirdpartyDao(){
        return new ThirdpartyDao();
    }

    @Bean
    public ThirdpartyService thirdpartyService(ThirdpartyDao thirdpartyDao){
        return new ThirdpartyService(thirdpartyDao);
    }
}

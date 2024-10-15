package com.zyf.manual.bean;

import com.zyf.manual.bean.entity.AnoAutoOriginBean;
import com.zyf.manual.bean.entity.AutoFacDIBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Created by @author yihui in 09:43 18/10/13.
 */
@SpringBootApplication
public class ManualGoodBeanApplication {

    public ManualGoodBeanApplication(AutoFacDIBean autoFacDIBean, AnoAutoOriginBean anoAutoOriginBean) {
        String str = autoFacDIBean.print();
        System.out.println(str);

        String m = anoAutoOriginBean.print();
        System.out.println(m);
    }

    public static void main(String[] args) {
        SpringApplication.run(ManualGoodBeanApplication.class, args);
    }

}

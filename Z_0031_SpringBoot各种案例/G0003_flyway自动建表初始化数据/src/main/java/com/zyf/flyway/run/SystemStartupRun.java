package com.zyf.flyway.run;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SystemStartupRun implements CommandLineRunner {

    @Autowired
    private Flyway flyway;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("开始自动建表");
        flyway.migrate();
        System.out.println("系统启动完成");

        {
            System.out.println("==================================================");
            System.out.println("查询 user 表");
            System.out.println("==================================================");
            final List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user");
            for (Map<String, Object> map : maps) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    System.out.print(entry.getKey() + ":" + entry.getValue());
                    System.out.print(",");
                }
                System.out.println(",");
            }
        }
    }
}

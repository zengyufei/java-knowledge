package com.zyf.flyway.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Autowired
    private DataSource dataSource;

    // 同步建表
    @Bean
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load();
    }

    // 异步自动建表
//    @Bean
//    public CompletableFuture<Flyway> flyway() {
//        // 在另一个线程中创建 MongoClient 实例
//        return CompletableFuture.supplyAsync(() -> Flyway.configure()
//                .dataSource(dataSource)
//                .locations("classpath:db/migration")
//                .load());
//    }
//
//    @PostConstruct
//    public void migrate() throws FlywayException {
//        System.err.println("执行 flyway...");
//        flyway().thenAcceptAsync(Flyway::migrate);
//    }

}

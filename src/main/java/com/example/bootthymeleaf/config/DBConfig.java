package com.example.bootthymeleaf.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.bootthymeleaf")
public class DBConfig {
    @Bean
    public DataSource getMySQLDataSource() {
        String engine = "mysql";
        String host = "localhost";
        String port = "3306";
        String schema = "test";
        String userName = "root";
        String pwd = "aaa000";

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:" + engine + "://" + host + ":" + port + "/" + schema + "?useUnicode=true&characterEncoding=utf8&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSourceBuilder.username(userName);
        dataSourceBuilder.password(pwd);
        return dataSourceBuilder.build();
    }
}

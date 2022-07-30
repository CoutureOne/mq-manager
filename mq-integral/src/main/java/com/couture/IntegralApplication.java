package com.couture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author Couture
 * @Created by 2022/6/24 10:52
 */
@SpringBootApplication
@MapperScan("com.couture.mapper")
public class IntegralApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntegralApplication.class, args);
    }
}

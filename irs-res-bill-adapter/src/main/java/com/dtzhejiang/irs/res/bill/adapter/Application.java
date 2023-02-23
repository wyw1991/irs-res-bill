package com.dtzhejiang.irs.res.bill.adapter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
        "com.dtzhejiang.irs.res.bill.adapter",
        "com.dtzhejiang.irs.res.bill.domain",
        "com.dtzhejiang.irs.res.bill.infra",
        "com.dtzhejiang.irs.res.bill.app",
        "com.alibaba.cola"
})
@MapperScan("com.dtzhejiang.irs.res.bill.infra.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

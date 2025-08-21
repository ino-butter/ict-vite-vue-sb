package com.example.springVueTest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.springVueTest.mapper")
public class SpringVueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringVueApplication.class, args);
	}

}

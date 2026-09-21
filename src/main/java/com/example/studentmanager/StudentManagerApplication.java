package com.example.studentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类
 * 程序入口
 */
@SpringBootApplication
public class StudentManagerApplication {
    /**
     * 程序入口方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentManagerApplication.class, args);
    }
}

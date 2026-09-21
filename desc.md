## 文件结构与作用

com.example.studentmanager/
├── StudentManagerApplication.java   ← 🚀 启动类（入口）
├── StudentController.java           ← 🎮 控制器（处理请求）
├── Student.java                     ← 📦 实体类（数据模型）
└── application.properties           ← ⚙️ 配置文件



### 三者关系图

浏览器发起请求: GET http://localhost:8080/students
                    │
                    ▼
        ┌───────────────────────────┐
        │  StudentManagerApplication │  ← ① 启动整个应用
        │  (main 方法)               │     开启 Tomcat 服务器（端口 8080）
        └───────────┬───────────────┘
                    │
                    ▼
        ┌───────────────────────────┐
        │    StudentController      │  ← ② 接收并处理请求
        │                           │     匹配 @GetMapping("/students")
        │    @RestController         │
        └───────────┬───────────────┘
                    │ 调用
                    ▼
        ┌───────────────────────────┐
        │        Student            │  ← ③ 提供数据
        │   (实体类 - 学生数据)       │     封装学生信息（学号、姓名等）
        └───────────────────────────┘
                    │
                    ▼
            返回 JSON 给浏览器
        [
          {"studentId":"001","name":"张三",...},
          {"studentId":"002","name":"李四",...}
        ]

### 各自职责

| 文件                      | 角色   | 职责                      | 什么时候用             |
| ------------------------- | ------ | ------------------------- | ---------------------- |
| StudentManagerApplication | 启动器 | 启动整个 Spring Boot 应用 | 只运行一次             |
| StudentController         | 控制类 | 处理 HTTP 请求，返回数据  | 每来一个请求都执行     |
| Student                   | 实体类 | 封装学生数据（属性+方法） | 在 Controller 里被使用 |

### 总结

`Application` 启动 → `Controller` 接收请求 → `Student` 提供数据 → 返回结果

## 经典三层架构

┌─────────────────────┐
│   Controller 层      │  ← 控制器，只写 @GetMapping/@PostMapping 等路由注解
│  (接收请求)           │
└─────────┬───────────┘
          │ 调用
          ▼
┌─────────────────────┐
│   Service 层         │  ← 业务层，只写业务逻辑，不能写路由注解！
│  (处理业务)           │
└─────────┬───────────┘
          │ 调用
          ▼
┌─────────────────────┐
│   Dao / Mapper 层    │  ← 数据层（以后连接数据库）
│  (操作数据)           │
└─────────────────────┘

### 各层职责

| 层级       | 注解            | 职责               |
| ---------- | --------------- | ------------------ |
| Controller | @RestController | 接收请求、返回响应 |
| Service    | @Service        | 处理业务逻辑       |
| Mapper/Dao | @Mapper         | 操作数据库         |


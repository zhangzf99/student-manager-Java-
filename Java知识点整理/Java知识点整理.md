# 学生管理系统 Java 知识点整理

> 面向对象：前端开发者，Java 0基础  
> 用前端知识类比讲解，更容易理解

---

## 📁 一、项目整体结构

```
student-manager/
├── src/main/java/com/example/studentmanager/
│   ├── StudentManagerApplication.java    # 项目启动入口
│   ├── Student.java                      # 学生实体类（数据模型）
│   ├── StudentController.java             # 控制器（接口）
│   ├── StudentService.java                # 业务逻辑层
│   ├── StudentMapper.java                 # 数据库操作层
│   ├── Result.java                       # 统一响应格式
│   ├── GlobalExceptionHandler.java       # 全局异常处理
│   ├── MybatisPlusConfig.java            # 配置类
│   └── Role.java                         # 枚举类
├── src/main/resources/
│   └── application.properties             # 配置文件
└── pom.xml                               # Maven 依赖配置
```

### 🏗️ 类比前端理解

| 前端概念 | Java 后端概念 | 说明 |
|---------|--------------|------|
| Vue/React 组件 | Controller | 接收请求，处理接口 |
| Vuex/Store 的 mutations | Service | 处理业务逻辑 |
| API 接口 | Controller | 对外暴露的 HTTP 接口 |
| 组件的 data() | Entity/Model | 存储数据的类 |
| axios 请求 | Controller | 接收前端请求 |

---

## 🎯 二、MVC 架构（核心思想）

MVC = Model（模型） + View（视图） + Controller（控制器）

```
前端请求 → Controller → Service → Mapper → 数据库
                ↓
            逐层返回
```

### 比喻理解

```
餐厅点餐：
├── Controller  = 服务员（接收你的点餐请求）
├── Service     = 厨师（处理做菜的业务逻辑）
├── Mapper      = 食材仓库管理员（从仓库拿食材）
└── 数据库      = 食材仓库（存放原材料）
```

### 1️⃣ Controller 层（控制器）

```java
@RestController  // 声明这是一个控制器
public class StudentController {

    @Autowired  // 自动注入 Service
    private StudentService studentService;

    @GetMapping("/queryAllStudents")  // 声明 GET 请求路径
    public Result<List<Student>> getAllStudents() {
        return Result.success(studentService.getAllStudents());
    }
}
```

**类似前端的代码：**
```javascript
// 相当于前端的一个接口函数
const getAllStudents = async () => {
  const res = await axios.get('/queryAllStudents')
  return res.data
}
```

---

## 📦 三、实体类 (Entity/Model)

实体类 = 前端的 `data()` 返回的数据结构

### Student.java
```java
@TableName("student")  // 对应数据库表名
public class Student {
    @TableId  // 标记这是主键
    private String studentId;   // 学号
    private String name;        // 姓名
    private int age;           // 年龄
    private String gender;      // 性别
    private String className;   // 班级
    
    // 无参构造方法（必须要有，Jackson 反序列化需要）
    public Student() {}
    
    // Getter 和 Setter（提供访问私有属性的方法）
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    // ... 其他 getter/setter
}
```

### 类比前端

```javascript
// 前端的 Student 数据结构
const student = {
  studentId: '1001',
  name: '张三',
  age: 18,
  gender: '男',
  className: '一班'
}
```

---

## 🏷️ 四、Java 注解 (@Annotation)

注解 = 前端的装饰器/指令（如 `v-bind:`, `@click`）

| 注解 | 作用 | 类比 |
|-----|------|-----|
| `@RestController` | 声明这个类是控制器 | 相当于 `export default` 导出组件 |
| `@GetMapping("/path")` | 声明 GET 请求接口 | 相当于 `axios.get('/path')` |
| `@PostMapping("/path")` | 声明 POST 请求接口 | 相当于 `axios.post('/path')` |
| `@Autowired` | 自动注入依赖 | 相当于 `import` + 自动实例化 |
| `@RequestParam` | 接收 URL 参数 | 相当于 `params: { id }` |
| `@RequestBody` | 接收 JSON 请求体 | 相当于 `data: body` |
| `@TableName("xxx")` | 指定数据库表名 | - |
| `@TableId` | 标记主键字段 | - |
| `@Service` | 声明这是服务层 | - |
| `@Mapper` | 声明这是数据库操作层 | - |

### 代码示例

```java
// 前端的请求
axios.post('/addStudent', { name: '张三', age: 18 })

// 后端接收
@PostMapping("/addStudent")
public Result<String> addStudent(@RequestBody Student student) {
    // @RequestBody 相当于解析 JSON 请求体
    return Result.success(studentService.addStudent(student));
}
```

---

## 🔙 五、统一响应格式 (Result<T>)

前端的接口通常这样返回：
```json
{
  "code": 200,
  "msg": "成功",
  "data": [...]
}
```

后端的 Result 类：
```java
public class Result<T> {
    private int code;      // 状态码
    private String msg;    // 消息
    private T data;        // 数据（泛型，可以是任意类型）

    // 成功响应
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }

    // 失败响应
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }
}
```

### 使用方式

```java
// 返回学生列表
return Result.success(studentService.getAllStudents());

// 返回错误信息
return Result.error("未找到学号为 " + id + " 的学生");

// 相当于前端返回
// { code: 200, msg: "成功", data: [...] }
```

---

## 🔧 六、泛型 (Generics)

泛型 = 前端的 `Type<T>` 类型定义

```java
// Result<List<Student>> = 返回一个装着 Student 列表的 Result
public Result<List<Student>> getAllStudents() {
    return Result.success(list);
}

// Result<Student> = 返回一个装着单个 Student 的 Result
public Result<Student> getStudentById(@RequestParam String id) {
    return Result.success(student);
}

// 前端 TypeScript 泛型
// interface Result<T> { code: number, msg: string, data: T }
```

---

## 💉 七、依赖注入 (@Autowired)

依赖注入 = 前端的 `import` + 自动实例化

```java
@RestController
public class StudentController {
    
    @Autowired  // 自动创建 StudentService 实例
    private StudentService studentService;
    
    // 使用时直接调用
    studentService.getAllStudents();
}
```

### 前端类比

```javascript
// 前端需要手动 import
import StudentService from './service/StudentService'

export default {
  mounted() {
    // 前端也是通过 service 调用
    this.studentService.getAllStudents()
  }
}
```

---

## 🗄️ 八、MyBatis-Plus（数据库操作）

MyBatis-Plus = 简化版的数据库 ORM 框架

### Mapper 接口
```java
@Mapper  // 声明这是数据库操作层
public interface StudentMapper extends BaseMapper<Student> {
    // 不需要写 SQL！MyBatis-Plus 自动提供 CRUD 方法
}

// studentMapper.selectById(id)      → SELECT * FROM student WHERE id = ?
// studentMapper.insert(student)     → INSERT INTO student VALUES (...)
// studentMapper.updateById(student) → UPDATE student SET ... WHERE id = ?
// studentMapper.deleteById(id)      → DELETE FROM student WHERE id = ?
```

### 前端类比

```javascript
// 相当于封装好的 API 函数
const studentAPI = {
  selectById: (id) => db.query('SELECT * FROM student WHERE id = ?', [id]),
  insert: (data) => db.query('INSERT INTO student VALUES (...)', [...]),
  update: (data) => db.query('UPDATE student SET ...', [...]),
  delete: (id) => db.query('DELETE FROM student WHERE id = ?', [id])
}
```

### 分页查询
```java
Page<Student> page = new Page<>(pageNum, pageSize);  // 创建分页对象
IPage<Student> result = studentMapper.selectPage(page, null);  // 执行分页查询
// SELECT * FROM student LIMIT (pageNum-1)*pageSize, pageSize
```

---

## ⚙️ 九、配置文件 (application.properties)

```properties
# 数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/student_manager
spring.datasource.username=root
spring.datasource.password=密码

# Redis 配置
spring.data.redis.host=localhost
spring.data.redis.port=6379

# 服务端口
server.port=8088
```

### 前端类比

```javascript
// 相当于前端的 .env 配置文件
VUE_APP_API_BASE_URL=http://localhost:8088
VUE_APP_DB_HOST=localhost
```

---

## 🚨 十、全局异常处理

统一捕获处理程序中的错误

```java
@RestControllerAdvice  // 声明全局异常处理器
public class GlobalExceptionHandler {
    
    // 处理空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Result<String> handleNullPointerException(NullPointerException e) {
        return Result.error("空指针异常，请检查数据");
    }
    
    // 处理其他所有异常（兜底）
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.error("系统异常：" + e.getMessage());
    }
}
```

### 前端类比

```javascript
// 相当于 axios 的响应拦截器
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.code === '500') {
      Message.error('系统异常')
    }
    return Promise.reject(error)
  }
)
```

---

## 📊 十一、HTTP 请求方法对照

| 前端请求 | 后端注解 | 用途 |
|---------|---------|-----|
| `axios.get('/users')` | `@GetMapping("/users")` | 查询数据 |
| `axios.post('/users', data)` | `@PostMapping("/users")` | 新增数据 |
| `axios.put('/users', data)` | `@PutMapping("/users")` | 修改数据 |
| `axios.delete('/users/1')` | `@DeleteMapping("/users/1")` | 删除数据 |

### 参数接收对照

```java
// GET 请求参数
@GetMapping("/getStudentById")
public Result<Student> getStudentById(@RequestParam String id) {
    // http://localhost:8088/getStudentById?id=1001
}

// POST 请求体
@PostMapping("/addStudent")
public Result<String> addStudent(@RequestBody Student student) {
    // 请求体：{ "name": "张三", "age": 18 }
}
```

---

## 🔐 十二、枚举类 (Enum)

枚举 = 一组固定的常量

```java
public enum Role {
    ADMIN("admin"),  // 管理员
    USER("user");    // 普通用户

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
```

### 前端类比

```javascript
// 前端的常量
const Role = {
  ADMIN: 'admin',
  USER: 'user'
}

// 使用
if (user.role === Role.ADMIN) {
  // 管理员操作
}
```

---

## 📝 十三、常用命令对比

| 操作 | MyBatis-Plus 方法 | SQL |
|-----|------------------|-----|
| 查询单个 | `selectById(id)` | `SELECT * FROM student WHERE id = ?` |
| 查询所有 | `selectList(null)` | `SELECT * FROM student` |
| 分页查询 | `selectPage(page, wrapper)` | `SELECT * FROM student LIMIT ? OFFSET ?` |
| 条件查询 | `selectList(wrapper)` | `SELECT * FROM student WHERE name LIKE '%?%'` |
| 新增 | `insert(entity)` | `INSERT INTO student VALUES (...)` |
| 修改 | `updateById(entity)` | `UPDATE student SET ... WHERE id = ?` |
| 删除 | `deleteById(id)` | `DELETE FROM student WHERE id = ?` |

---

## 🎓 总结

### 前端 → 后端知识映射

```
前端 Vue 组件                    →  Java Controller
前端 methods 中的业务函数         →  Java Service
前端调用的 API 函数              →  Java Mapper (数据库操作)
前端 data 中的数据结构           →  Java Entity (实体类)
前端 vuex/mutations             →  Java Service
前端 api/index.js               →  Java Controller (接口定义)
前端 .env 配置文件              →  Java application.properties
```

### 核心理解

1. **Controller** = 对外暴露接口，接收请求，返回响应
2. **Service** = 处理业务逻辑（类似 Vue 的 methods）
3. **Mapper** = 操作数据库（类似封装好的 SQL 函数）
4. **Entity** = 数据模型（类似前端的 data）
5. **注解** = 声明式配置（类似装饰器/指令）

---

> 💡 **学习建议**：对照前端代码来理解后端，会更容易掌握！

package com.example.studentmanager;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * 学生控制器
 * 处理学生相关的 HTTP 请求
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 查询所有学生
    @GetMapping("/queryAllStudents")
    public Result<List<Student>> getAllStudents() {
        return Result.success(studentService.getAll());
    }

    // 根据学号查询学生
    @GetMapping("/getStudentById")
    public Result<Student> getStudentById(@RequestParam String id) {
        Student student = studentService.getById(id);
        if (student == null) {
            return Result.<Student>error("未找到学号为 " + id + " 的学生");
        }
        return Result.success(student);
    }

    // 添加学生
    @PostMapping("/addStudent")
    public Result<String> addStudent(@RequestBody Student student) {
        return Result.success(studentService.add(student));
    }

    // 修改学生信息
    @PostMapping("/updateStudent")
    public Result<String> updateStudent(@RequestBody Student student) {
        return Result.success(studentService.update(student));
    }

    // 删除学生
    @PostMapping("/deleteStudent")
    public Result<String> deleteStudent(@RequestParam String id) {
        return Result.success(studentService.delete(id));
    }

    // 分页查询
    @GetMapping("/getStudentsByPage")
    public Result<IPage<Student>> getStudentsByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "2") int pageSize) {
        return Result.success(studentService.getStudentsByPage(pageNum, pageSize));
    }

    // 条件搜索
    @GetMapping("/searchStudents")
    public Result<IPage<Student>> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "2") int pageSize) {
        return Result.success(studentService.searchStudents(name, className, gender, pageNum, pageSize));
    }

    // 测试多态
    @GetMapping("/testPolymorphism")
    public Result<String> testPolymorphism() {
        // // 没有多态的话就需要这样写，每多一种人，就要多写一个方法。有了多态，一个Person类就能处理所有的子类
        // Student s = new Student("1001", "张三", 20, "男", "1班");
        // Teacher t = new Teacher("2001", "数学", "李四", 30, "男");
        // return Result.success(s.introduce() + "\n" + t.introduce());

        // 多态：父类引用指向子类对象
        Person p1 = new Student("1001", "张三", 20, "男", "1班");
        Person p2 = new Teacher("2001", "数学", "李四", 30, "男");
        return Result.success(p1.introduce() + "\n" + p2.introduce());
    }

    // 测试重载
    @GetMapping("/testOverload")
    public Result<String> testOverload() {
        Student s1 = studentService.getById("001");
        Student s2 = studentService.getById("张三", "高一1班");
        Student s3 = studentService.getById("001", true);

        return Result.success("s1=" + (s1 != null ? s1.getName() : "null")
                + ", s2=" + (s2 != null ? s2.getName() : "null")
                + ", s3=" + (s3 != null ? s3.getName() : "null"));
    }

    @GetMapping("/testPassByValue")
    public Result<String> testPassByValue() {
        // 1. 基本类型
        int num = 10;
        studentService.changeInt(num);
        System.out.println("方法外 num = " + num);  // 还是 10

        // 2. 对象：修改属性
        Student s1 = new Student("001", "张三", 18, "男", "高一1班");
        studentService.changeStudent(s1);
        System.out.println("方法外 s1.name = " + s1.getName());  // 被修改了

        // 3. 对象：重新赋值
        Student s2 = new Student("002", "李四", 19, "女", "高一2班");
        studentService.changeStudent2(s2);
        System.out.println("方法外 s2.name = " + s2.getName());  // 还是李四

        return Result.success("测试完成，看控制台输出");
    }
}
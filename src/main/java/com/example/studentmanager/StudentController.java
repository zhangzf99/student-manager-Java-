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
        return Result.success(studentService.getAllStudents());
    }

    // 根据学号查询学生
    @GetMapping("/getStudentById")
    public Result<Student> getStudentById(@RequestParam String id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return Result.<Student>error("未找到学号为 " + id + " 的学生");
        }
        return Result.success(student);
    }

    // 添加学生
    @PostMapping("/addStudent")
    public Result<String> addStudent(@RequestBody Student student) {
        return Result.success(studentService.addStudent(student));
    }

    // 修改学生信息
    @PostMapping("/updateStudent")
    public Result<String> updateStudent(@RequestBody Student student) {
        return Result.success(studentService.updateStudent(student));
    }

    // 删除学生
    @PostMapping("/deleteStudent")
    public Result<String> deleteStudent(@RequestParam String id) {
        return Result.success(studentService.deleteStudent(id));
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

}
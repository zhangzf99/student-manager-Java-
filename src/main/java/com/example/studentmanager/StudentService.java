package com.example.studentmanager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 学生业务层
 * 处理学生相关的业务逻辑
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_ALL = "student:all";

    /**
     * 条件搜索 + 分页
     */
    public IPage<Student> searchStudents(String name, String className, String gender, int pageNum, int pageSize) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();

        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (className != null && !className.isEmpty()) {
            wrapper.eq("class_name", className);
        }
        if (gender != null && !gender.isEmpty()) {
            wrapper.eq("gender", gender);
        }

        return studentMapper.selectPage(page, wrapper);
    }

    /**
     * 分页查询学生
     */
    public IPage<Student> getStudentsByPage(int pageNum, int pageSize) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        return studentMapper.selectPage(page, null);
    }

    /**
     * 查询所有学生
     */
    public List<Student> getAllStudents() {

        // return studentMapper.selectList(null);
        // 1. 先查缓存
        List<Student> cached = (List<Student>) redisTemplate.opsForValue().get(CACHE_KEY_ALL);
        if (cached != null) {
            System.out.println("从缓存中获取数据");
            return cached;
        }

        // 2. 缓存没有，查数据库
        List<Student> students = studentMapper.selectList(null);
        System.out.println("从数据库中获取数据");

        // 3. 写入缓存，设置 60 秒过期
        redisTemplate.opsForValue().set(CACHE_KEY_ALL, students, 60, TimeUnit.SECONDS);

        System.out.println(students.get(0));

        return students;
    }

    /**
     * 根据学号查询学生
     */
    public Student getStudentById(String id) {
        return studentMapper.selectById(id);
    }

    /**
     * 添加学生
     */
    public String addStudent(Student student) {
        // return studentMapper.insert(student) > 0 ? "添加成功！" : "添加失败！";
        studentMapper.insert(student);
        redisTemplate.delete(CACHE_KEY_ALL);
        return "添加成功";
    }

    /**
     * 删除学生
     */
    public String deleteStudent(String id) {
        // return studentMapper.deleteById(id) > 0 ? "删除成功！" : "删除失败，学号不存在！";
        studentMapper.deleteById(id);
        redisTemplate.delete(CACHE_KEY_ALL);
        return "删除成功";
    }

    /**
     * 修改学生信息
     */
    public String updateStudent(Student student) {
        // return studentMapper.updateById(student) > 0 ? "修改成功！" : "修改失败，学号不存在！";
        studentMapper.updateById(student);
        redisTemplate.delete(CACHE_KEY_ALL);
        return "修改成功";
    }
}

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
 * 实现 PersonService 接口
 */
@Service
public class StudentService implements PersonService<Student> {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_ALL = "student:all";

    // ==================== 实现接口方法 ====================

    @Override
    public List<Student> getAll() {
        // 先查缓存
        List<Student> cached = (List<Student>) redisTemplate.opsForValue().get(CACHE_KEY_ALL);
        if (cached != null) {
            System.out.println("从缓存中获取数据");
            return cached;
        }
        // 缓存没有，查数据库
        List<Student> students = studentMapper.selectList(null);
        System.out.println("从数据库中获取数据");
        // 写入缓存
        redisTemplate.opsForValue().set(CACHE_KEY_ALL, students, 60, TimeUnit.SECONDS);
        return students;
    }

    @Override
    public Student getById(String id) {
        return studentMapper.selectById(id);
    }

    @Override
    public String add(Student student) {
        studentMapper.insert(student);
        redisTemplate.delete(CACHE_KEY_ALL);
        return "添加成功";
    }

    @Override
    public String update(Student student) {
        studentMapper.updateById(student);
        redisTemplate.delete(CACHE_KEY_ALL);
        return "修改成功";
    }

    @Override
    public String delete(String id) {
        studentMapper.deleteById(id);
        redisTemplate.delete(CACHE_KEY_ALL);
        return "删除成功";
    }

    // ==================== 原有方法 ====================

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

    // 根据学号查询
    public Student getById(int id) {
        return studentMapper.selectById(id);
    }

    // 重载：根据姓名和班级查询
    public Student getById(String name, String className) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        wrapper.eq("class_name", className);
        return studentMapper.selectOne(wrapper);
    }

    // 重载：根据学号查询，并指定是否走缓存
    public Student getById(String id, boolean useCache) {
        if (useCache) {
            System.out.println("走缓存查询");
        }
        return studentMapper.selectById(id);
    }

    // 值传递vs引用传递
    // 验证值传递：基本类型
    public void changeInt(int num) {
        num = 100;
        System.out.println("方法内 num = " + num);
    }

    // 验证引用传递：对象
    public void changeStudent(Student student) {
        student.setName("被修改了");  // 会改变原对象
    }

    // 验证引用传递：重新赋值
    public void changeStudent2(Student student) {
        student = new Student();      // 不会改变原对象
        student.setName("新对象");
        System.out.println("方法内 student.name = " + student.getName());
    }
}

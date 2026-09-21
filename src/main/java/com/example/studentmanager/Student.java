package com.example.studentmanager;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 学生实体类
 * 用于存储学生信息（学号、姓名、年龄、性别、班级）
 */
@TableName("student")
public class Student {
    @TableId
    // ==================== 成员变量 ====================
    private String studentId;   // 学号
    private String name;         // 姓名
    private int age;            // 年龄
    private String gender;      // 性别
    private String className;   // 班级

    
    // 无参构造方法（Jackson 反序列化需要）
    public Student() {
    }
    // ==================== 构造方法 ====================
    /**
     * 全参构造方法
     * 创建对象时自动调用，用于初始化所有属性
     */
    public Student(String studentId, String name, int age, String gender, String className) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.className = className;
        this.age = age;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student{");
        sb.append("studentId='").append(studentId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", className='").append(className).append('\'');
        sb.append('}');
        return sb.toString();
    }

    

    // ==================== Getter & Setter ====================
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}

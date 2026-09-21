package com.example.studentmanager;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 学生实体类
 * 用于存储学生信息（学号、姓名、年龄、性别、班级）
 */
@TableName("student")
public class Student extends Person {
    @TableId
    // ==================== 成员变量 ====================
    private String studentId;   // 学号
    private String className;         // 姓名

    
    // 无参构造方法（Jackson 反序列化需要）
    public Student() {
    }
    // ==================== 构造方法 ====================
    /**
     * 全参构造方法
     * 创建对象时自动调用，用于初始化所有属性
     */
    public Student(String studentId, String name, int age, String gender, String className) {
        super(name, age, gender); // 调用父类构造方法
        this.studentId = studentId;
        this.className = className;
    }

    @Override // 表示重写父类方法
    public String introduce() {
        return super.introduce() + "，我是一个学生，我的班级是" + className + "，我的学号是" + studentId;
    }

   public String getStudentId() {
    return studentId;
   }

   public void setStudentId(String studentId) {
    this.studentId = studentId;
   }

   public String getClassName() {
    return className;
   }

   public void setClassName(String className) {
    this.className = className;
   }

   @Override 
   public String toString() {
    return "Student{" +
            "studentId='" + studentId + '\'' +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", gender='" + gender + '\'' +
            ", className='" + className + '\'' +
            '}';
   }
}

package com.example.studentmanager;

public class Teacher extends Person {
  protected String teacherId;
  protected String subject;

  public Teacher(){}

  public Teacher(String teacherId, String subject, String name, int age, String gender) {
    super(name, age, gender);
    this.teacherId = teacherId;
    this.subject = subject;
  }

  @Override 
  public String introduce() {
    return super.introduce() + "，我是一个老师，我的工号是" + teacherId + "，我教的科目是" + subject;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  @Override 
  public String toString() {
    return "Teacher{" +
            "teacherId='" + teacherId + '\'' +
            ", subject='" + subject + '\'' +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", gender='" + gender + '\'' +
            '}';
  }
}

package com.example.studentmanager;

public abstract class AbstractPerson {
  protected String name;
  protected int age;
  protected String gender;

  public AbstractPerson() {}

  public AbstractPerson(String name, int age, String gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  // 抽象方法，子类必须实现
  public abstract String getRole();

  // 普通方法：子类可以直接用
  public String introduce() {
    return "我是一个" + getRole() + "，我的名字是" + name + "，我的年龄是" + age + "，我的性别是" + gender;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }
}

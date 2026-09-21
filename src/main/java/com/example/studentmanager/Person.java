package com.example.studentmanager;

public class Person {
  protected String name;  // protected 修饰的属性可以被子类访问
  protected int age;
  protected String gender;

  public Person(){}

  public Person(String name, int age, String gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  // 通用方法：自我介绍
  public String introduce() {
    return "我叫" + name + "，今年" + age + "岁，性别" + gender;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public String getName(String name) {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGender(String gender) {
    this.gender = gender;
  } 

}

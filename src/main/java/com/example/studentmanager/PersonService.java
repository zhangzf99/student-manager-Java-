package com.example.studentmanager;

import java.util.List;

public interface PersonService<T> {
  // 查询所有
  List<T> getAll();

  // 根据ID查询
  T getById(String id);

  // 添加
  String add(T person);

  // 修改
  String update(T person);

  // 删除
  String delete(String id);
}
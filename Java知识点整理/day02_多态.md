## 多态

多态是Java面向对象三大特征之一（封装、继承、多态），它的核心思想可以用一句话概括：**同一个方法调用，在不同的对象上表现出不同的行为**

在 `StudentController` 里写了这段代码：

```java
Person p1 = new Student("001", "张三", 18, "男", "高一1班");
Person p2 = new Teacher("T001", "李老师", 35, "女", "数学");

System.out.println(p1.introduce());
System.out.println(p2.introduce());
```

关键点：

- p1 和 p2 的类型都是 Person(父类)
- 但它们实际指向的是 Student 和 Teacher 对象(子类)
- 调用 introduce() 时，实际执行的是子类重写后的方法，而不是父类的 introduce()

### 多态的三大条件

| 条件                 | 说明                   | 场景表现                    |
| -------------------- | ---------------------- | --------------------------- |
| 继承                 | 子类继承父类           | Student extends Person      |
| 重写                 | 子类重写父类方法       | @Override introduce()       |
| 父类引用指向子类引用 | 用父类类型接收子类实例 | Person p = new Student(...) |

### 没有多态会怎样

```java
Student s = new Student("001", "张三", 18, "男", "高一1班");
Teacher t = new Teacher("T001", "李老师", 35, "女", "数学");

System.out.println(s.introduce());  // 只能处理 Student
System.out.println(t.introduce());  // 只能处理 Teacher
```

每多一种人（比如 `Doctor`、`Driver`），就要多写一个方法。**有了多态，一个 `Person` 类型就能处理所有子类**。

### 多态的好处

#### 代码更通用

比如你写一个方法，接收 `Person` 类型：

```java
public void printIntroduce(Person person) {
    System.out.println(person.introduce());
}
```

这个方法**可以传 `Student`、`Teacher`、`Doctor` 等任何 `Person` 的子类**，不用为每种类型单独写一个方法。

#### 更易扩展

以后要加 `Doctor`，只需要：

- 新建 `Doctor extends Person`
- 重写 `introduce()`

**不需要改 `printIntroduce()` 方法**。

#### 框架里大量使用

你项目里的 `StudentMapper extends BaseMapper<Student>`，Spring 的 `@Autowired` 注入，都是多态的体现。

### 多态的两种形式

#### 编译时多态(方法重载)

同名方法，参数不同：

```java
public void print(int a) { }
public void print(String s) { }
```

#### 运行时多态(方法重写)

刚才的场景就是这种：

```java
Person p = new Student(...);  // 编译时看 Person，运行时看 Student
p.introduce();                 // 实际执行 Student 的 introduce()
```

### 多态的限制

**只能调用父类里定义的方法**

比如 `Student` 里有 `getStudentId()`，但 `Person` 里没有。用 `Person` 类型引用时，**不能直接调用 `getStudentId()`**

```java
Person p = new Student(...);
p.getStudentId();  // 编译报错，因为 Person 里没有这个方法
```

**想调用子类特有方法，需要强制类型转换：**

```java
Person p = new Student(...);
Student s = (Student) p;  // 强转
s.getStudentId();          // 现在可以调用了
```

| 概念       | 说明                                                 |
| ---------- | ---------------------------------------------------- |
| 多态是什么 | 父类引用指向子类对象，调用方法时执行子类重写后的逻辑 |
| 三大条件   | 继承、重写、父类引用指向子类对象                     |
| 好处       | 代码通用、易扩展、框架大量使用                       |
| 限制       | 只能调用父类定义的方法，子类特有方法需要强转         |

### 一句话理解

**多态就是“同一个方法，不同对象，不同表现”。** 你写代码时面向父类，运行时实际执行子类的逻辑，这样代码更通用、更灵活。
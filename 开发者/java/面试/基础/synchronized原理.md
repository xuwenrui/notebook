### 1. synchronized 原理概述

`synchronized` 的基本思想是在对象上附加一个互斥锁（mutex lock），当一个线程试图访问同步代码块或方法时，它必须先获得该对象的锁。如果已经有另一个线程持有了该锁，则当前线程进入阻塞状态，直到持有锁的线程释放锁为止。

### 2. synchronized 锁的类型

`synchronized` 可以分为两种类型的锁：

- **对象级锁**：针对对象实例加锁，每次调用都必须获取对象实例的锁。
- **类级锁**：针对类的 Class 对象加锁，每次调用都必须获取类的锁。
### 3. synchronized 的使用方式

#### 3.1 同步方法

在类的方法声明前加上 `synchronized` 关键字，可以将整个方法体变为同步代码块。

java

深色版本

```
1public class MyClass {
2    private int count = 0;
3
4    public synchronized void increment() {
5        count++;
6    }
7}
```

#### 3.2 同步代码块

在方法体内使用 `synchronized` 关键字来同步特定代码块。

java

深色版本

```
1public class MyClass {
2    private Object lock = new Object();
3    private int count = 0;
4
5    public void increment() {
6        synchronized (lock) {
7            count++;
8        }
9    }
10}
```

### 4. synchronized 的内部实现

#### 4.1 锁的状态

`Object` 类中有一个监视器（Monitor），它维护了锁的状态。锁有四种状态：

- **无锁状态**：没有任何线程持有锁。
- **偏向锁**：如果锁对象始终由一个线程访问，JVM 会认为该锁是“偏向锁”，此时锁会偏向于第一个获得它的线程。
- **轻量级锁**：当锁存在竞争时，JVM 会尝试使用 CAS（Compare and Swap）操作来获取锁。
- **重量级锁**：当轻量级锁无法满足需求时，会升级为重量级锁，此时会涉及操作系统层面的线程阻塞。

#### 4.2 锁的获取和释放

- **获取锁**：当线程试图获取一个对象的锁时，它会检查锁的状态。如果锁是无锁状态或偏向锁，线程可以直接获取锁。如果是轻量级锁，则使用 CAS 尝试获取。如果 CAS 失败，则升级为重量级锁。
- **释放锁**：当线程退出同步代码块或方法时，它会释放锁。如果锁是轻量级锁，则通过 CAS 释放；如果是重量级锁，则通过操作系统来释放。

### 5. synchronized 的优缺点

#### 5.1 优点

- **简单易用**：`synchronized` 的语法非常简单，不需要额外的锁管理。
- **自动解锁**：当线程退出同步代码块或方法时，锁会自动释放，减少了死锁的风险。

#### 5.2 缺点

- **性能开销**：在高并发情况下，重量级锁会导致线程频繁阻塞和唤醒，影响性能。
- **死锁风险**：虽然 `synchronized` 有助于防止死锁，但如果使用不当（如嵌套锁顺序不一致），仍然可能导致死锁。

### 6. synchronized 与 volatile 的区别

`volatile` 是 Java 中用于实现变量可见性的关键字，它保证了多线程环境下的可见性和禁止指令重排序。`synchronized` 则不仅提供了可见性，还提供了原子性和有序性。

### 7. Java 6 之后的优化

从 Java 6 开始，HotSpot 虚拟机对 `synchronized` 进行了一系列优化，包括：

- **偏向锁**：减少无竞争情况下的同步开销。
- **轻量级锁**：使用 CAS 尝试获取锁，减少重量级锁的使用。
- **锁膨胀**：当轻量级锁不够用时，才会升级为重量级锁。
- **适应性自旋锁**：在等待锁时，线程可以尝试自旋等待一段时间再阻塞。


### 对象锁 vs 类锁
#### 3.1 锁的作用范围

- **对象锁**：作用于特定的对象实例，多个线程可以同时访问不同对象实例的同步方法或同步代码块。
- **类锁**：作用于整个类的所有实例，多个线程不能同时访问同一个类的静态同步方法或静态同步代码块。
#### 3.2 并发访问的粒度
- **对象锁**：具有更高的并发度，因为多个线程可以同时访问不同的对象实例。
- **类锁**：并发度较低，因为所有线程都需要竞争同一个锁。


### 普通方法上的 `synchronized`
当在普通方法上使用 `synchronized` 时，锁的作用域是当前对象实例。这意味着，如果有多个线程同时访问不同实例的方法，这些线程可以并发地执行这些方法，因为它们锁定的是不同的对象实例。
```java
public class MyClass {
    private int count = 0;

    // 使用 synchronized 修饰符的普通方法
    public synchronized void increment() {
        count++;
    }
}

// 创建两个不同的对象实例
MyClass obj1 = new MyClass();
MyClass obj2 = new MyClass();

// 两个线程分别访问不同的对象实例
new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        obj1.increment();
    }
}).start();

new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        obj2.increment();
    }
}).start();
```
在这个示例中，increment 方法是一个同步方法，它锁定的是当前对象实例 obj1 和 obj2。因此，两个线程可以并发地执行 obj1.increment() 和 obj2.increment()，因为它们锁定的是不同的对象实例。

### 静态方法上的 `synchronized`
当在静态方法上使用 synchronized 时，锁的作用域是类的 Class 对象。这意味着，如果有多个线程同时访问同一个类的不同静态方法，这些线程必须排队等待，因为它们锁定的是同一个类的 Class 对象。
```java
public class MyClass {
    private static int count = 0;

    // 使用 synchronized 修饰符的静态方法
    public static synchronized void increment() {
        count++;
    }
}

// 调用静态方法
new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        MyClass.increment();
    }
}).start();

new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        MyClass.increment();
    }
}).start();
```
在这个示例中，increment 方法使用 synchronized (this) 显式锁定当前对象实例，而 staticIncrement 方法使用 synchronized (MyClass.class) 显式锁定类的 Class 对象。


### 成员变量作为锁
如果使用类的成员变量作为锁对象，并且每次调用类方法时都获取的是同一个成员变量的引用，那么并不会每次都创建新的对象锁。相反，成员变量的引用指向的是同一个对象实例，因此所有的同步操作都会竞争同一个锁。
```java
public class MyClass {
    private int count = 0;
    private final Object lock = new Object();  // 成员变量作为锁

    public void increment() {
        synchronized (lock) {  // 使用成员变量作为锁
            count++;
        }
    }

    public int getCount() {
        synchronized (lock) {  // 使用相同的锁
            return count;
        }
    }
}

// 创建一个对象实例
MyClass obj = new MyClass();

// 两个线程分别访问同一个对象实例
new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        obj.increment();
    }
}).start();

new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        obj.increment();
    }
}).start();
```

在这个示例中，`MyClass` 类中的 `lock` 是一个成员变量，并且在 `increment` 方法和 `getCount` 方法中都被用作锁对象。因此，两个线程在调用 `increment` 方法时，会竞争同一个锁。

### 静态方法上的锁
当在静态方法上使用 `synchronized` 关键字时，锁定的是类的 `Class` 对象。这意味着所有调用该类的静态同步方法的线程都会竞争同一个锁。但是，静态方法上的 `synchronized` 锁并不会影响类的非静态方法或者实例方法上的锁。

当一个静态方法被 `synchronized` 修饰时，它锁定的是类的 `Class` 对象。这意味着所有对该类的静态同步方法的调用都会受到同一个锁的影响。具体来说：

- 如果多个线程同时调用同一个类的不同静态同步方法，它们会排队等待。
- 如果一个线程正在执行某个静态同步方法，其他线程将无法执行同一个类的其他静态同步方法，直到当前线程释放锁。
```java
public class MyClass {
    private static int staticCount = 0;
    private int instanceCount = 0;

    // 静态同步方法
    public static synchronized void staticIncrement() {
        staticCount++;
    }

    // 实例同步方法
    public synchronized void increment() {
        instanceCount++;
    }

    // 获取静态计数器的值
    public static int getStaticCount() {
        return staticCount;
    }

    // 获取实例计数器的值
    public int getInstanceCount() {
        return instanceCount;
    }
}
```
在这个示例中：

- `thread1` 调用 `MyClass.staticIncrement()`，这是静态同步方法，锁定的是 `MyClass.class`。
- `thread2` 调用 `obj.increment()`，这是实例同步方法，锁定的是 `obj`。

由于 `thread1` 和 `thread2` 锁定的是不同的对象，因此它们可以并发执行。`thread1` 和 `thread2` 之间不会互相阻塞。
### synchronized锁升级的概念

锁升级是 Java 虚拟机（JVM）为了提高锁的性能而引入的一种机制。锁升级主要包括以下几个阶段：

1. **无锁状态**：没有线程持有锁。
2. **偏向锁**：当一个线程第一次访问同步块时，如果同步块之前没有被任何线程访问过，那么该线程可以获得偏向锁。偏向锁可以减少无竞争情况下的同步开销。
3. **轻量级锁**：当多个线程竞争同一个锁时，JVM 会尝试使用 CAS（Compare and Swap）操作来获取锁。轻量级锁可以减少重量级锁的使用频率。
4. **重量级锁**：当轻量级锁无法满足需求时，锁会被升级为重量级锁，此时会涉及操作系统层面的线程阻塞。


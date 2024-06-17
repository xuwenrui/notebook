Java 15中引入了一个新的预览特性：密封类（Sealed Classes）。这个特性是通过JEP 360（Sealed Classes (Preview)）实现的。密封类是一种限制类继承的新机制，允许开发者指定哪些类可以继承一个特定的类或实现一个特定的接口。这种方式为类的继承体系提供了更细粒度的控制，有助于设计更为健壮、可维护的代码库。

### 密封类的目的

密封类的主要目的包括：

1. **限制继承**：允许创建者定义类的继承层级，确保只有预期的类能够继承该类或实现该接口，从而提高系统的可预测性和安全性。
2. **设计意图**：更明确地表达类设计的意图，限制不必要的扩展，帮助文档化和实施设计决策。
3. **优化编译器和运行时性能**：由于类的继承结构更为明确，编译器和运行时系统可以做出更有效的优化。

### 基本用法

密封类使用`sealed`关键字修饰，并且需要指定允许哪些子类继承它。子类可以使用`permits`关键字来列出允许的子类列表。例如：
```java
// 密封类声明
public sealed class Shape permits Circle, Rectangle, Triangle {}

// 允许的子类
final class Circle extends Shape {}
final class Rectangle extends Shape {}
non-sealed class Triangle extends Shape {}

// 不在允许列表中的类无法继承Shape
class UnallowedShape extends Shape {} // 编译错误
```
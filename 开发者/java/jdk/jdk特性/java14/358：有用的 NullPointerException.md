Java 14 引入了一个预览特性，称为“有用的NullPointerException”（JEP 358）。这一特性旨在增强`NullPointerException`（空指针异常）的错误信息，使其包含更多上下文信息，帮助开发者更快地定位并解决问题。在传统的Java版本中，当程序抛出`NullPointerException`时，异常信息往往较为简略，仅指出发生了空指针异常，但未明确指出哪个变量为`null`。这在大型代码库或复杂方法调用链中寻找问题根源时可能相当耗时。

### 有用的信息增强

Java 14中的“有用的NullPointerException”改进了这一点，当发生空指针异常时，异常信息会尝试提供以下额外信息：

- **哪个变量导致了空指针异常**：通过分析字节码，JVM能够识别出是哪一行代码中的哪个变量尝试访问了`null`引用。
- **更精确的位置信息**：除了变量名，异常信息还可能包括更多上下文，如方法调用链，帮助快速定位问题发生的具体位置。

### 示例

假设在Java 14中，有如下代码：
```java
public class Test {
    public static void main(String[] args) {
        String text = null;
        System.out.println(text.length());
    }
}
```
在Java 14之前，如果这段代码运行，你可能会看到类似这样的错误信息：
```java
Exception in thread "main" java.lang.NullPointerException
    at Test.main(Test.java:4)
```

而在Java 14中，得益于“有用的NullPointerException”特性，错误信息可能被增强为：
```java
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.length()" because "text" is null
    at Test.main(Test.java:4)
```
这里明确指出是因为`text`变量为`null`，尝试调用`length()`方法引发了异常，这为开发者提供了直接的线索，无需进一步调试即可迅速定位问题。

### 注意事项

- 该特性在Java 14中是预览状态，需要通过`--enable-preview`编译选项启用，并且在运行时也需要开启预览特性。
- 开发者应该意识到，虽然这一特性提高了调试效率，但最佳实践仍然是在编码时采取预防措施，比如使用Optional类或进行空值检查，以避免空指针异常的发生。

“有用的NullPointerException”特性是Java语言朝着提供更友好、更易调试的错误信息迈出的重要一步，它能够显著提升开发者的日常工作效率。
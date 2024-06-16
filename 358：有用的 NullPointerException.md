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
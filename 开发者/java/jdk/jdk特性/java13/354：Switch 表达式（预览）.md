Java 13继续了Java 12中开始的Switch表达式预览特性，并对其进行了迭代和完善。在Java 13中，Switch表达式作为一个预览功能再次出现，这次带来了一些改进和稳定性的增强，为开发者提供了更灵活和强大的控制流语句来替代传统的Switch语句。

### Java 13中Switch表达式的改进

1. **yield关键字的改进**：Java 13中的Switch表达式进一步细化了`yield`关键字的使用，允许在case块内有更复杂的逻辑而不必立刻返回值，提高了代码的可读性和灵活性。
    
2. **更广泛的预览和反馈**：基于Java 12中预览功能的反馈，Java 13中的Switch表达式进行了调整，以更好地适应开发者的需求和预期，这包括语法上的微调以及对编译器和运行时行为的优化。
    
3. **模式匹配的初步探索**：虽然模式匹配（JEP 305）作为一个独立的特性在Java 16中才成为正式功能，Java 13的Switch表达式预览也为将来模式匹配的整合打下了基础。模式匹配使得Switch表达式能够更加自然地处理不同类型和结构的数据。
    

### 示例

在Java 13中，Switch表达式的使用更加灵活，比如：
```java
var dayDescription = switch (dayOfWeek) {
    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> {
        // 更复杂的逻辑可以放在这里
        yield "Workday";
    }
    case SATURDAY, SUNDAY -> "Weekend";
    default -> throw new IllegalArgumentException("Invalid day of week");
};
```
在这个例子中，可以看到在case块内部可以有多条语句，并且使用`yield`来指定从当前case块返回的值。此外，对于不符合前面列出的任何情况，可以使用`default`分支来处理。

**<font color="#c0504d">使用新的switch语法，不但不需要break，还可以直接返回值。</font>**


### 注意事项

- 要在Java 13中使用Switch表达式预览功能，需要在编译时使用`--enable-preview`标志，并且在运行时也需要启用预览特性。
- 预览功能意味着它还在演进中，可能在后续的Java版本中会有变动，因此在生产环境中使用时需要谨慎。

Java 13对Switch表达式的进一步预览体现了Java语言对现代编程实践的适应和改进，为开发者提供了更优雅和强大的控制结构，以应对日益复杂的软件需求。


## [yield](https://so.csdn.net/so/search?q=yield&spm=1001.2101.3001.7020)

大多数时候，在switch表达式内部，我们会返回简单的值。

但是，如果需要复杂的语句，我们也可以写很多语句，放到{…}里
```java
public class Main {
    public static void main(String[] args) {
        String fruit = "orange";
        int opt = switch (fruit) {
            case "apple" -> 1;
            case "pear", "mango" -> 2;
            default -> {
                int code = fruit.hashCode();
                yield code; // switch语句返回值
            }
        };
        System.out.println("opt = " + opt);
    }
}
```
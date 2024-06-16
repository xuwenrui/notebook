Java 12 引入了Switch表达式的预览功能，这是对传统Switch语句的一个重要改进，旨在让Switch表达式更加灵活和强大。这个特性实际上是在Java 14中成为正式特性（标准功能），但在Java 12和Java 13中作为预览功能提供给开发者尝试和反馈。
### Java 12中的Switch表达式预览特性
在Java 12中，Switch表达式允许你使用更简洁和声明性的方式来编写Switch结构，主要新特性包括：

1. **yield关键字**：用于从Switch表达式中返回值。这使得Switch表达式不仅能控制流程，还能作为表达式的一部分直接返回值。 
2. **多 case匹配**：允许单个case标签匹配多个值。 
3. **简化case块**：如果case块只有一条语句，并且该语句是返回值，那么大括号可以省略。 
### 示例

在Java 12之前，你可能这样写Switch语句：
```java
String day;
switch (dayOfWeek) {
    case MONDAY:
    case FRIDAY:
        day = "Workday";
        break;
    case SATURDAY:
    case SUNDAY:
        day = "Weekend";
        break;
    default:
        day = "Unknown";
        break;
}
```
而在Java 12及之后，你可以使用更简洁的Switch表达式：
```java
var day = switch (dayOfWeek) {
    case MONDAY, FRIDAY -> "Workday";
    case SATURDAY, SUNDAY -> "Weekend";
    default -> "Unknown";
};
```
请注意，上述代码展示了Java 14及以后正式采用的语法，因为Java 12中作为预览功能的Switch表达式可能在细节上有所不同，但核心概念和用法相似。预览功能意味着在该版本中可以试验此特性，但需要显式开启，并且可能会在后续版本中根据反馈进行调整。

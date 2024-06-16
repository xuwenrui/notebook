Java 11 对Unicode的支持包括了Unicode 10.0及之前的标准。Unicode 10.0于2017年发布，引入了8518个字符，包括新的表情符号、货币符号等。在Java中使用Unicode字符，主要涉及到字符串的创建、比较、正则表达式匹配等方面。下面是一些基本示例说明如何在Java 11中使用Unicode 10特性：
```java
String str1 = "café"; // 使用预组合字符é
String str2 = "cafe\u0301"; // é 分解为字母e和组合 acute accent
System.out.println(str1.equals(str2)); // 输出可能为false，取决于具体实现
System.out.println(str1.equalsIgnoreCase(str2)); // 同样，可能因实现而异
String normalizedStr1 = str1.normalize();
String normalizedStr2 = str2.normalize();
System.out.println(normalizedStr1.equals(normalizedStr2)); // 经标准化后，应为true
```
### 注意事项

- 确保你的IDE和编译器支持UTF-8编码，以便正确显示和处理Unicode字符。
- 在进行字符串比较时，考虑是否需要先进行Unicode规范化，特别是当字符串可能包含不同形式的相同字符时。
- 考虑到性能和兼容性，使用特定的Unicode属性或类别时，检查所用Java版本是否完全支持Unicode 10的所有特性。
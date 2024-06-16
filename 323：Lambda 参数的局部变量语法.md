lambda 局部变量支持类型推断

```java
List<String> list= Arrays.asList("a","b");  
list.stream().map((String a)->a.toLowerCase()).collect(Collectors.toList());  
list.stream().map((a)->a.toLowerCase()).collect(Collectors.toList());  
// java11  
list.stream().map((var a)->a.toLowerCase()).collect(Collectors.toList());
```
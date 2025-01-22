从Java 11（Java SE 11，也称为JDK 11）开始，Nashorn JavaScript引擎被官方标记为已弃用（deprecated）。Nashorn最初在Java 8中引入，作为一个轻量级的JavaScript运行环境，用于在Java应用程序中嵌入JavaScript代码执行，支持ECMAScript 5.1规范。

### 弃用原因

Nashorn的弃用主要有以下几个原因：

1. **维护成本**：随着JavaScript语言的迅速发展和ECMAScript标准的不断进化，维护一个符合最新标准的JavaScript引擎需要大量的资源和工作。Nashorn团队难以持续跟上这种快速变化的步伐。
    
2. **使用率**：尽管Nashorn提供了一种在Java中执行JavaScript的便捷方式，但其实际使用率并不高，相比之下，Node.js等专门的JavaScript运行时环境更为流行。
    
3. **社区和战略方向**：Oracle决定将资源集中于更有战略意义的项目和技术上，对于一个相对小众且维护成本较高的特性，选择弃用是合理的资源调配决策。
    

### 后续替代方案

对于那些依赖Nashorn的应用程序，官方建议考虑以下几种替代方案：

1. **外部进程调用**：可以通过Java的`Runtime.exec()`或`ProcessBuilder`类来调用外部的Node.js或其他JavaScript运行时环境，以执行JavaScript代码。
    
2. **GraalVM**：GraalVM是Oracle实验室的项目，它提供了一个高性能的JavaScript和WebAssembly运行时环境，支持ECMAScript的最新版本，并且可以作为Java应用程序的一部分嵌入使用。GraalVM被认为是Nashorn的一个强大替代品，不仅因为它对现代JavaScript标准的支持更好，还因为它提供了诸如提前编译（AOT）等功能，进一步提升了性能。
    

### 注意事项

尽管Java 11中Nashorn被弃用，但它并未立即从JDK中移除，仍在Java 11中可用。然而，按照Java的弃用政策，它很可能在未来的Java版本中被完全移除，因此，依赖Nashorn的应用程序应当尽早规划迁移至其他解决方案。
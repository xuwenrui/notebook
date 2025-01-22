### 初始化

当你创建一个 `ArrayList` 时，如果没有显式地指定初始容量，那么它将有一个默认的初始容量。在 Java 的 `ArrayList` 中，默认的初始容量是 10。

```
1List<String> list = new ArrayList<>(); // 默认初始容量为 10
2List<String> listWithInitialCapacity = new ArrayList<>(20); // 显式指定初始容量为 20
```

### 扩容机制

当 `ArrayList` 中的元素数量超过了它的当前容量时，`ArrayList` 会自动进行扩容。扩容通常涉及以下几个步骤：

1. **计算新容量**：新的容量通常是当前容量的一定倍数，Java 标准库中的 `ArrayList` 通常会将容量扩大为原来的 1.5 倍。
2. **创建新数组**：根据新的容量创建一个新的数组。
3. **复制旧数组内容**：将旧数组的所有元素复制到新数组中。
4. **替换引用**：使 `ArrayList` 的内部引用指向新的数组。

#### 扩容的具体实现

在 `ArrayList` 的源码中，扩容是通过 `ensureExplicitCapacity` 方法来实现的，这个方法会检查当前的容量是否足够，如果不够则触发扩容操作。

```
1private void ensureCapacityInternal(int minCapacity) {
2    modCount++;
3
4    // 如果最小容量大于当前容量
5    if (minCapacity - elementData.length > 0)
6        grow(minCapacity);
7}
8
9private void grow(int minCapacity) {
10    // overflow-conscious code
11    int oldCapacity = elementData.length;
12    int newCapacity = oldCapacity + (oldCapacity >> 1); // 扩容至1.5倍
13    if (newCapacity - minCapacity < 0)
14        newCapacity = minCapacity;
15    if (newCapacity - MAX_ARRAY_SIZE > 0)
16        newCapacity = hugeCapacity(minCapacity);
17    // minCapacity is usually close to size, so this is a win:
18    elementData = Arrays.copyOf(elementData, newCapacity);
19}
```

### 性能考量

频繁的扩容操作会导致性能下降，因为每次扩容都需要创建新的数组并复制旧数组的内容。因此，在预计集合的大小时，如果能够大致估计出集合将包含多少元素，最好在创建 `ArrayList` 时就指定一个接近实际大小的初始容量，这样可以减少扩容次数，提高程序的性能。

### 示例代码

java

深色版本

```
1import java.util.ArrayList;
2import java.util.List;
3
4public class ArrayListExample {
5
6    public static void main(String[] args) {
7        // 预计添加1000个元素
8        List<String> list = new ArrayList<>(1000);
9
10        for (int i = 0; i < 1000; i++) {
11            list.add("Element " + i);
12        }
13
14        System.out.println("List size: " + list.size());
15        System.out.println("List capacity: " + ((ArrayList<?>)list).getMaxSize());
16    }
17}
```
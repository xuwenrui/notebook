#### Promise的介绍
Promise是异步编程的一种解决方案，它的构造函数是同步执行的，then方法是异步执行的，所以Promise创建后里面的函数会立即执行，构造函数中的resolve和reject只有第一次执行有效，也就是说Promise状态一旦改变就不能再变
#### Promise的作用
 promise主要是用来解决回调地狱的问题，通过使用.then来使得代码成链式调用，方便维护和使用。.then中的回调函数属于异步任务中的微任务
#### Promise的状态
promise一共有3个状态：pending、fulfilled和rejected

**pedding->初始状态**：调用promise时，一开始就呈现出等待状态，遇到resolve或者reject之前，都处于这个状态，且可以改变，但如果确定了状态（fulfilled/reject），则结果将永远不变，不能再次修改

**fulfilled->成功状态**：在执行了resolve后，promise则会从pedding变成fulfilled，后续会进入.then 的回调函数中，在回调函数的第一个参数函数中可以获取到值

**rejected->失败状态**：在执行了reject后，promise状态会变成rejected，rejected函数传递的参数，可以在.then的第二个参数函数中获取的到，或者是在.catch获取到，但是如果程序上的错误，得通过.catch函数去拿到失败消息，在.then中是获取不了的

#### Promise的九个方法

- ##### Promise.resolve
这个Promise对象的静态方法，用于创建一个成功状态的Promise对象，可以之间在.then的成功回调中，获取resolve的值
#### Promise的介绍
Promise是异步编程的一种解决方案，它的构造函数是同步执行的，then方法是异步执行的，所以Promise创建后里面的函数会立即执行，构造函数中的resolve和reject只有第一次执行有效，也就是说Promise状态一旦改变就不能再变
#### Promise的作用
 promise主要是用来解决回调地狱的问题，通过使用.then来使得代码成链式调用，方便维护和使用。.then中的回调函数属于异步任务中的微任务
 #### Promise的状态
 
 
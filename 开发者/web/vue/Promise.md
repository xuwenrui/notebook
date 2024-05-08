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
```javascript
const p = Promise.resolve("成功");
p.then((res) => {
  console.log("----打印：", res); //----打印： 成功
});
 
//该用法类似于
const p1 = new Promise((resolve, reject) => {
  resolve("成功");
});
p1.then((res) => {
  console.log("----打印：p1", res); //----打印：p1 成功
});
 
//后续代码中都会出现类似，或者另外中写法，尝试的时候，不能同时放出来执行
```

- ##### Promise.reject
这个方法跟Promise.resolve一样，只是作用不同，属于拒绝的状态；可以直接在.then的失败回调中，获取reject的值；也可以在.catch中获取；如果两者同时出现代码中，看看是catch写在前面还是.then函数写在前面 ---业务中，拒绝状态用.then去执行回调，异常用.catch
```javascript
const p = Promise.reject("失败");
p.then(
  (res) => {
    console.log("----打印：", res); //不执行
  },
  (rej) => {
    console.log("----打印：", rej); //----打印： 失败
  }
);
 
//另外写法 
p.then(
  (res) => {
    console.log("----打印：p", res); //不执行
  },
  (rej) => {
    console.log("----打印：p", rej); //----打印：p 失败
  }
).catch((error) => {
  console.log("----打印：catch", error); //不执行
});
 
//另外写法---基本没有吧catch写在第一个
p.catch((error) => {
  console.log("----打印：catch", error); //----打印：catch 失败
}).then(
  (res) => {
    console.log("----打印：p", res); //不执行
  },
  (rej) => {
    console.log("----打印：p", rej); //不执行
  }
);
 
//另外写法
p.then((res) => {
  console.log("----打印：p", res); //不执行
}).catch((error) => {
  console.log("----打印：catch", error); //----打印：catch 失败
});
 
//该用法类似于
const p1 = new Promise((resolve, reject) => {
  reject("失败");
});
p1.then(
  (res) => {
    console.log("----打印：p1", res); //不执行
  },
  (rej) => {
    console.log("----打印：p1", rej); //----打印：p1 失败
  }
);
```


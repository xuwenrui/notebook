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

- ##### Promise.then
函数回调执行，常用于接收请求接口返回的数据；该回调函数有两个参数（函数），一个是用于处理 Promise 解决时的回调函数，另一个是可选的用于处理 Promise 拒绝（rejected）时的回调函数；用于接收promise对应状态的数据。而且.then的返回值也是个promis对象，具体看后面返回值详解章节
```javascript
const p = new Promise((resolve, reject) => {
  resolve("成功");
});
const result = p.then(
  (res) => {
    console.log("----打印：p", res); //----打印：p 成功
   
  },
  (rej) => {
    console.log("----打印：p", rej); //不执行
  }
);
result.then(
  (res) => {
    console.log("----打印：第二次", res); //----打印:第二次 undefined
    console.log("----打印：", result); //----打印： Promise { <fulfilled> } --    
    [PromiseState]]: "fulfilled"[[PromiseResult]]: undefined
 
    //为啥呢？
    //因为第一个then没有给返回的具体值--，所以在第二个.then中，
    //剥离掉promise，拿到的result就是undefined
    //虽然.then返回值是promise，但.then里面的参数，拿到的是promise里面携带的值（通俗表达）
 
  },
  (rej) => {
    console.log("----打印：第二次", rej); //不执行
  }
);
 
//这个是最先打印--所以执行这里的时候，显示还是在pedding状态
console.log("----打印：", result); //----打印： Promise { <pending> } --[PromiseState]]: "fulfilled"[[PromiseResult]]: undefined
 
//若这么写。第二个then函数中拿到值就是6，而不是undefined
 const result = p.then(
   (res) => {
     console.log("----打印：p", res); //----打印：p 成功
     return 6
    },
 );
 result.then(
   (res) => {
     console.log("----打印：第二次", res); //----打印:第二次 6
   },
 );
 
```

**Tips：注意，如果.then中写了参数不是函数，则会变成promise穿透哦！**
``` javascript
const p = new Promise((resolve, reject) => {
  resolve("成功");
});
const result = p.then(Promise.resolve("传不过去")); //不是函数
result.then((res) => {
  console.log("----打印：", res); //----打印： 成功
});
//相当于这中写法
const result1 = p.then(null); //不是函数
result1.then((res) => {
  console.log("----打印：", res); //----打印： 成功
});
//也可以写成链式调用，结果一样的
p.then(null).then((res) => {
  console.log("----打印：", res); //----打印： 成功
});
```

- ##### Promise.catch
用于注册在 Promise 对象拒绝（rejected）时的回调函数。同时也可以用来捕获代码异常或者出错；

      1、像如果promise是一个reject的状态或者抛出异常或者错误，既可以在.then函数中的第二个参数获取，也可以在.catch中的函数中获取，如果两者同时出现代码中（可以看Promise.reject中的案例哦）。

  2、.then中产生异常能在.catch 或者下一个.then中捕获。.then和.catch本质上是没有区别的， 需要分场合使用;一般异常用.catch。拒绝状态用.then

      3、而且，一旦异常被捕获，则未执行后面中的.then不管多少，都不会执行。一般最好用.catch 在最后去捕获，这样能捕获到上面最先报错的信息
```
const p = new Promise((resolve, reject) => {
  reject("拒绝");
  console.log("----打印："); //会输出
  throw new Error("抛出错误"); //这一句改变promise状态，因为状态已经决定了
});
p.catch((error) => {
  console.log(error); // ：--拒绝
});
 
// 另外写法
p.then(
  (res) => {},
  (rej) => {
    console.log("----打印：", rej); //----打印： 拒绝
  }
);
 
//另外情况
const p1 = new Promise((resolve, reject) => {
  throw new Error("抛出错误");
});
 
p1.catch((error) => {
  console.log("p1", error); //：Error: 抛出错误
});
 
//另外情况---2
const p2 = new Promise((resolve, reject) => {
  resolve(11);
});
 
p2.then((res) => {
  console.log("----打印：", res);
  throw new Error("抛出错误");
})
  //如果没有下面这个.then 则错误就会被catch捕获
  //不提倡这种写法--只是为了证明，then也可以接收到异常
  .then(
    (res) => {},
    (rej) => {
      console.log("----打印：能接到就执行", rej); //----打印：能接到就执行 Error: 抛出错误
    }
  )
  .catch((error) => {
    console.log("catch接到", error); //不执行
  });
 
//另外的情况3
const p3 = new Promise((resolve, reject) => {
  resolve(11);
});
 
p3.then((res) => {
  console.log("----打印：", res);
  throw new Error("抛出错误");
})
  //如果没有下面这个.then 则错误就会被catch捕获
  //一般是直接在最后写.catch，而不会这么一层层写reject回调函数，除非特殊业务
  .then(
    (res) => {},
    (rej) => {
      console.log("----打印：rej", rej); //执行
    }
  )
  .then(
    (res) => {},
    (rej) => {
      console.log("----打印：会不会执行"); //不会执行
    }
  )
  .catch((error) => {
    console.log("catch接到", error); //不执行
  });
```
- ##### Promise.all
  Promise.all接收一个promise对象的数组作为参数，当这个数组里面的promise对象，没有出现rejected状态，则会一直等待所有resolve成功后，才执行.then这个回调，如果有一个是rejected状态，则会先执行.all里面的.then中第二个回调函数或者.catch函数，不会等后续跑完你在执行

    传递给Promise.all的 promise并不是一个个的顺序执行的，而是同时开始、并行执行的
```javascript
var p1 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    resoleve("p1--3000");
  }, 3000);
});
var p2 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    resoleve("p2--1000");
  }, 1000);
});
var p3 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    console.log("----打印：看看是先执行失败，还是全部执行完再catch");
    resoleve("p3--5000");
  }, 5000);
});
 
//第一情况
 var promiseArr = [p1, p2, p3];
 console.time("promiseArr");
  Promise.all(promiseArr)
    .then((res) => {
      console.log("res", res); //res [ 'p1--3000', 'p2--1000', 'p3--5000' ]
      console.timeEnd("promiseArr"); // promiseArr: 5.020s
    })
    .catch((err) => console.log(err));
 
//另外情况
var p4 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    reject("p4--2000");
  }, 2000);
});
 
var promiseArr = [p1, p2, p3, p4];
console.time("promiseArr");
Promise.all(promiseArr)
  .then((res) => {
    console.log("res", res);
    console.timeEnd("promiseArr");
  })
  .catch((err) => console.log(err)); 
 
//打印顺序
//p4--2000
//输出----打印：看看是先执行失败，还是全部执行完再catch 
 
//解释：p3的输出，比上边catch晚输出因此，如果有失败状态，就会提前结束、去执行all里面的回调函数
```

- ##### Promise.any
- Promise.any接收一个promise的数组作为参数，只要其中有一个Promise成功执行，就会返回已经成功执行的Promise的结果；若全部为rejected状态，则会到最后的promise执行完，全部的promise返回到异常函数中；可用于多通道获取数据，谁先获取就执行下一步程序，跳出这个过程。---和all的相反
```
var p1 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    resoleve("p1--3000");
  }, 3000);
});
var p2 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    reject("p2--1000");
  }, 1000);
});
var p3 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    console.log("----打印p3");
    resoleve("p3--5000");
  }, 5000);
});
 
var promiseArr = [p1, p2, p3];
console.time("promiseArr");
Promise.any(promiseArr)
  .then((res) => {
    console.log("res", res); //res [ 'p1--3000', 'p2--1000', 'p3--5000' ]
    console.timeEnd("promiseArr"); // promiseArr: 5.020s
  })
  .catch((err) => console.log(err));
 
//输出顺序 --虽然p2已经执行完，但是为rejected状态，而any会返回第一个resolve状态的对象
//   res p1--3000
// promiseArr: 3.009s
// ----打印p3
 
//另外一种情况
var p1 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    reject("p1--3000");
  }, 3000);
});
var p2 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    reject("p2--1000");
  }, 1000);
});
var p3 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    console.log("----打印p3");
    reject("p3--5000");
  }, 5000);
});
 
var promiseArr = [p1, p2, p3];
console.time("promiseArr");
Promise.any(promiseArr)
  .then((res) => {
    console.log("res", res); //res [ 'p1--3000', 'p2--1000', 'p3--5000' ]
    console.timeEnd("promiseArr"); // promiseArr: 5.020s
  })
  .catch((err) => console.log(err));
 
//输出结果   解释--因为p1，2，3都是错误，所以any一直在等有成功的状态，所以知道p3结束后，没有成功的，就走catch那边
// ----打印p3
// [AggregateError: All promises were rejected] {
//   [errors]: [ 'p1--3000', 'p2--1000', 'p3--5000' ]
// }
```

- ###### Promise.race
  方法接收的参数和.all、.any接收的参数一样，接收一个可迭代promise对象的数组，**当任何一个promise的状态先确定（拒绝或者成功），则会执行.race中的回调函数**，具体根据promise的状态 ---和allSettled效果互斥
```
var p1 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    console.log("----打印：p1");
    resoleve("p1--3000");
  }, 3000);
});
 
let p2 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    reject("p2--1000");
  }, 1000);
});
 
Promise.race([p1, p2])
  .then((res) => {
    console.log("----打印：res", res);
  })
  .catch((err) => {
    console.log("----打印：err", err);
  });
 
//执行结果
//----打印：err p2--1000
//----打印：p1
 
//另外情况
 
let p3 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    resoleve("p3--500");
  }, 500);
});
 
Promise.race([p1, p2, p3])
  .then((res) => {
    console.log("----打印：res", res);
  })
  .catch((err) => {
    console.log("----打印：err", err);
  });
 
//打印结果
// ----打印：res p3--500
// ----打印：p1
```
- ###### Promise.allSettled
该方法参数也是和.all相同；顾名思义，这个方法是等所有promise参数确定状态后，才会执行回调函数，不管是成功的状态还是拒绝的状态，都等待全部执行后，并返回一个包含每个 Promise 解决状态的对象数组，每个对象包含两个属性：status 和 value；state表示promise的状态：resolve和rejected，value代表的是promise传递的值。

   请注意，Promise.allSettled 是 ES2020（也称为 ES11）中引入的新方法，需要支持该版本的 JavaScript 运行环境才能使用
   ```
   var p1 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    console.log("----打印：p1");
    resoleve("p1--3000");
  }, 3000);
});
 
let p2 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    reject("p2--1000");
  }, 1000);
});
 
let p3 = new Promise((resoleve, reject) => {
  setTimeout(() => {
    resoleve("p3--500");
  }, 500);
});
 
let p4 = new Promise((resolve, reject) => {
  throw new Error("抛出错误");
});
 
Promise.allSettled([p1, p2, p3, p4])
  .then((result) => {
    console.log("----打印：result", result);
  })
  .catch((err) => {
    console.log("----打印：", err); //不执行
  });
 
//执行结果
// ----打印：p1
// ----打印：result [
//   { status: 'fulfilled', value: 'p1--3000' },
//   { status: 'rejected', reason: 'p2--1000' },
//   { status: 'fulfilled', value: 'p3--500' },
//   {
//     status: 'rejected',
//     reason: Error: 抛出错误
//   }
// ]
```
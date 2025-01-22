#####  React的prop
在React中，prop（property的简写）是从外部传递给组件的数据，一个React组件通过定义自己能够接受的prop就定义了自己的对外公共接口。

###### 1．给prop赋值
```html
<SampleButton
  id="sample" borderWidth={2} onClick={onButtonClick}
  style={{color: "red"}}
/>
```
创建了名为SampleButton的组件实例，使用了名字分别为id、border Width、onClick和style的prop

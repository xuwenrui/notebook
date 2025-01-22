### 1. 父组件向子组件传递数据（Props）

这是最常见的组件间通信方式之一，通过 props 将父组件的数据传递给子组件。


假设我们有一个父组件 `Parent.vue` 和一个子组件 `Child.vue`。

```vue
1<template>
2  <div>
3    <h1>{{ message }}</h1>
4    <child-component :message="message" />
5  </div>
6</template>
7
8<script setup>
9import ChildComponent from './Child.vue';
10
11const message = 'Hello from Parent!';
12</script>
```

**Child.vue**

``` vue
<template>
  <div>
    <p>{{ message }}</p>
  </div>
</template>

<script setup>
defineProps({
9  message: String,
10});
11</script>
```

### 2. 子组件向父组件传递数据（事件）

子组件可以通过触发事件的方式将数据传递给父组件，父组件监听这些事件并做出相应的处理。
**Parent.vue**

```vue
1<template>
2  <div>
3    <child-component @updateMessage="handleUpdate" />
4    <p>{{ message }}</p>
5  </div>
6</template>
7
8<script setup>
9import { ref } from 'vue';
10import ChildComponent from './Child.vue';
11
12const message = ref('Hello from Parent!');
13
14function handleUpdate(newMessage) {
15  message.value = newMessage;
16}
17</script>
```

**Child.vue**
```vue 
1<template>
2  <div>
3    <input v-model="newMessage" @input="emitUpdate">
4  </div>
5</template>
6
7<script setup>
8import { defineEmits } from 'vue';
9
10const emit = defineEmits(['updateMessage']);
11const newMessage = '';
12
13function emitUpdate(event) {
14  emit('updateMessage', event.target.value);
15}
16</script>
```

### 3. 兄弟组件间通信（通过父组件）

兄弟组件之间可以直接通过父组件作为中介来通信。
**Parent.vue**

```vue
1<template>
2  <div>
3    <sibling-a @shareData="handleShareData" />
4    <sibling-b :sharedData="sharedData" />
5  </div>
6</template>
7
8<script setup>
9import { ref } from 'vue';
10import SiblingA from './SiblingA.vue';
11import SiblingB from './SiblingB.vue';
12
13const sharedData = ref('');
14
15function handleShareData(data) {
16  sharedData.value = data;
17}
18</script>
```

**SiblingA.vue**

```
1<template>
2  <div>
3    <input v-model="dataToShare" @input="emitShareData">
4  </div>
5</template>
6
7<script setup>
8import { defineEmits } from 'vue';
9
10const emit = defineEmits(['shareData']);
11const dataToShare = '';
12
13function emitShareData(event) {
14  emit('shareData', event.target.value);
15}
16</script>
```

**SiblingB.vue**

vue

深色版本

```
1<template>
2  <div>
3    <p>{{ sharedData }}</p>
4  </div>
5</template>
6
7<script setup>
8const props = defineProps({
9  sharedData: String,
10});
11</script>
```

### 4. 使用全局状态管理（Vuex）

对于更复杂的应用，可以使用 Vuex 进行全局的状态管理。

#### 示例代码
**store.js**
```js
1import { createStore } from 'vuex';
2
3const store = createStore({
4  state: {
5    message: 'Initial Message',
6  },
7  mutations: {
8    setMessage(state, message) {
9      state.message = message;
10    },
11  },
12  actions: {
13    updateMessage({ commit }, message) {
14      commit('setMessage', message);
15    },
16  },
17});
18
19export default store;
```

**main.js**

```
1import { createApp } from 'vue';
2import App from './App.vue';
3import store from './store';
4
5createApp(App).use(store).mount('#app');
```

**App.vue**

```
1<template>
2  <div>
3    <message-component />
4  </div>
5</template>
6
7<script setup>
8import MessageComponent from './components/MessageComponent.vue';
9</script>
```

**MessageComponent.vue**

vue

深色版本

```vue
1<template>
2  <div>
3    <button @click="updateMessage">Change Message</button>
4    <p>{{ message }}</p>
5  </div>
6</template>
7
8<script setup>
9import { computed } from 'vue';
10import { useStore } from 'vuex';
11
12const store = useStore();
13
14const message = computed(() => store.state.message);
15
16function updateMessage() {
17  store.dispatch('updateMessage', 'New Message');
18}
19</script>
```

### 5. 使用自定义事件总线（Event Bus）

如果不想使用 Vuex，可以创建一个全局的事件总线来处理组件间的通信。

#### 示例代码

**eventBus.js**

```
1import { createApp } from 'vue';
2
3const eventBus = createApp({});
4
5export default eventBus;
```

**Parent.vue**


```vue
1<template>
2  <div>
3    <child-a @shareData="handleShareData" />
4    <child-b :sharedData="sharedData" />
5  </div>
6</template>
7
8<script setup>
9import { ref } from 'vue';
10import ChildA from './ChildA.vue';
11import ChildB from './ChildB.vue';
12import eventBus from './eventBus';
13
14const sharedData = ref('');
15
16function handleShareData(data) {
17  sharedData.value = data;
18}
19
20eventBus.config.globalProperties.$on('shareData', handleShareData);
21</script>
```

**ChildA.vue**


```
1<template>
2  <div>
3    <input v-model="dataToShare" @input="emitShareData">
4  </div>
5</template>
6
7<script setup>
8import eventBus from './eventBus';
9
10const dataToShare = '';
11
12function emitShareData(event) {
13  eventBus.config.globalProperties.$emit('shareData', event.target.value);
14}
15</script>
```

**ChildB.vue**

vue

深色版本

```
1<template>
2  <div>
3    <p>{{ sharedData }}</p>
4  </div>
5</template>
6
7<script setup>
8const props = defineProps({
9  sharedData: String,
10});
11</script>
```
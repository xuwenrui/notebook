以下是 Spring 支持的事务传播行为：

#### 1. REQUIRED（默认）：

@Transactional(propagation = Propagation.REQUIRED) public void methodA() { // 业务逻辑 }

#### 2. SUPPORTS：

@Transactional(propagation = Propagation.SUPPORTS) public void methodB() { // 业务逻辑 }

3. MANDATORY：

@Transactional(propagation = Propagation.MANDATORY) public void methodC() { // 业务逻辑 }

4. REQUIRES_NEW：

@Transactional(propagation = Propagation.REQUIRES_NEW) public void methodD() { // 业务逻辑 }

5. NOT_SUPPORTED：

@Transactional(propagation = Propagation.NOT_SUPPORTED) public void methodE() { // 业务逻辑 }

6. NEVER：

@Transactional(propagation = Propagation.NEVER) public void methodF() { // 业务逻辑 }

7. NESTED：

@Transactional(propagation = Propagation.NESTED) public void methodG() { // 业务逻辑 }

|                    |      |                                    |                                                           |
| ------------------ | ---- | ---------------------------------- | --------------------------------------------------------- |
|                    | 当前事务 | 处理                                 | 备注                                                        |
| REQUIRED（默      认） | 存在   | 加入该事务                              |                                                           |
|                    | 没有   | 创建新事务                              |                                                           |
| SUPPORTS           | 存在   | 加入该事务                              |                                                           |
|                    | 没有   | 以非事务的方式执行                          |                                                           |
| MANDATORY          | 存在   | 加入该事务                              |                                                           |
|                    | 没有   | 抛出异常                               |                                                           |
| REQUIRES_NEW       | 存在   | 创建新事务，将该事务挂起，执行新的事务，新事务结束后再恢复原有事务。 | 无论当前是否存在事务，都创建一个新的事务。如果当前存在事务，将该事务挂起，执行新的事务，新事务结束后再恢复原有事务 |
|                    | 没有   | 创建新事务                              |                                                           |
| NOT_SUPPORTED      | 存在   | 将其挂起，以非事务的方式执行                     | 以非事务的方式执行操作，如果当前存在事务，将其挂起。这样做可以在一个没有事务的上下文中执行操作。          |
|                    | 没有   | 以非事务的方式执行                          |                                                           |
| NEVER              | 存在   | 抛出异常                               | 以非事务的方式执行操作，如果当前存在事务，则抛出异常。该传播行为确保方法在没有事务的上下文中执行          |
|                    | 没有   | 非事务的方式执行                           |                                                           |
| NESTED             | 存在   | 在嵌套事务内执行                           | 嵌套事务是相对于外部事务的，嵌套事务有自己的提交和回滚，但嵌套事务的提交不会对外部事务产生影响。          |
|                    | 没有   | 创建新事务                              |                                                           |
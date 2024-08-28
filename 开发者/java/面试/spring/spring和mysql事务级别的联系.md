spring事务级别

Spring 框架提供了多个事务级别，可以在使用 @Transactional 注解或者编程式事务管理时指定。这些事务级别定义了在事务执行期间允许的隔离程度，以及在并发访问时可能出现的问题。以下是 Spring 支持的常见事务级别：

#### 1. DEFAULT（默认级别）：

- 如果没有指定事务级别，默认使用数据库的默认事务级别。通常情况下，这是数据库的默认隔离级别，例如，在大多数数据库中，默认是 READ COMMITTED。

#### READ_UNCOMMITTED（读未提交）：

- 允许事务读取其他事务未提交的更改。这可能导致"脏读"、"不可重复读"和"幻读"的问题。

@Transactional(isolation = Isolation.READ_UNCOMMITTED) public void someTransactionalMethod() { // 事务逻辑 }

#### READ_COMMITTED（读已提交）：

- 允许事务读取其他已提交事务的更改，但防止"脏读"。这是大多数数据库的默认隔离级别。

#### REPEATABLE_READ（可重复读）：

- 保证在事务执行期间查询的结果保持一致。防止"脏读"和"不可重复读"，但允许出现"幻读"。

#### SERIALIZABLE（串行化）：

- 最高的隔离级别，确保在事务执行期间查询的结果是一致的。防止"脏读"、"不可重复读"和"幻读"。

在使用 @Transactional 注解时，你可以通过设置 isolation 属性来指定事务的隔离级别。如果未指定，将使用数据库的默认隔离级别。请注意，不是所有数据库都支持所有的隔离级别，因此需要确保所选择的隔离级别在特定数据库中受支持。

在编程式事务管理中，可以使用 TransactionDefinition 接口和 TransactionTemplate 来指定事务级别。

TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager); transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED); transactionTemplate.execute(status -> { // 事务逻辑 return null; });

spring事务级别和数据库事务级别有什么联系

Spring 中的事务隔离级别和数据库事务隔离级别是相关的，但它们并不是完全一样的。Spring 事务管理器通过使用底层数据库连接来实现事务隔离。在 Spring 中，你可以通过 @Transactional 注解或者编程式事务管理来设置事务隔离级别。

下面是 Spring 事务隔离级别和数据库事务隔离级别之间的联系：

1. Spring 事务隔离级别：

- Spring 定义了五个事务隔离级别，分别是 

DEFAULT

READ_UNCOMMITTED

READ_COMMITTED

REPEATABLE_READ

SERIALIZABLE

- 这些级别与 SQL 标准事务隔离级别相对应，但 

DEFAULT 

- 数据库事务隔离级别：

- 数据库通常定义了四个标准的事务隔离级别，分别是 

READ_UNCOMMITTED

READ_COMMITTED

REPEATABLE_READ

SERIALIZABLE

- 数据库的默认隔离级别通常是 

READ_COMMITTED

- Spring 和数据库的映射：

- 当在 Spring 中指定事务隔离级别时，Spring 会将这个级别传递给底层的数据库连接，要求数据库以相应的级别执行事务。
- 数据库连接驱动程序将负责确保事务隔离级别得到正确设置。

#### 脏读

脏读（Dirty Read）是指在一个事务中读取了另一个事务中未提交的数据。当一个事务修改了数据，而这个修改尚未提交，另一个事务读取了这个未提交的数据，就发生了脏读。脏读可能导致读取到无效或不一致的数据，因为尚未提交的修改可能最终会被回滚。

-- Session 1 START TRANSACTION; 
UPDATE accounts SET balance = balance - 100 WHERE account_id = 123; 
-- Session 2 (脏读) SELECT balance FROM accounts WHERE account_id = 123; -- 可能读取到未提交的更改 balance - 100 COMMIT; -- 或 ROLLBACK

不可重复读

不可重复读（Non-repeatable Read）是指在一个事务中，由于其他事务的修改，同一查询操作可能返回不同的结果。这种情况发生在两次相同的查询之间，因为在这两次查询之间，其他事务可能对数据进行了修改并提交。

-- Session 1 START TRANSACTION; 
SELECT * FROM products WHERE product_id = 1; 
-- Session 2 START TRANSACTION; 
UPDATE products SET price = price + 10 WHERE product_id = 1; COMMIT; -- Session 1 (不可重复读) SELECT * FROM products WHERE product_id = 1; -- 返回的结果与上一次查询不同 COMMIT;

在上述例子中，Session 1 在事务中执行了两次相同的查询，但在两次查询之间，Session 2 修改了产品1的价格并提交了事务。因此，第二次查询返回的结果与第一次查询不同，导致了不可重复读的问题。

### 幻读

幻读（Phantom Read）是指在一个事务中，由于其他事务的插入或删除操作，同一查询可能返回不同数量的记录。这与不可重复读的区别在于，幻读通常涉及到一系列记录的插入或删除，而不是单个记录的修改。

-- Session 1 START TRANSACTION; 
SELECT * FROM products WHERE category = 'Electronics'; 
-- Session 2 START TRANSACTION; INSERT INTO products (name, category) VALUES ('Smartphone', 'Electronics'); COMMIT; 
-- Session 1 (幻读) SELECT * FROM products WHERE category = 'Electronics'; -- 返回的结果中多了一条记录 COMMIT;

在上述例子中，Session 1 在事务中执行了两次相同的查询，但在两次查询之间，Session 2 插入了一个新的电子产品记录并提交了事务。因此，第二次查询返回的结果中多了一条记录，导致了幻读的问题。

Mysql 的事务隔离级别

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

|   |   |   |   |
|---|---|---|---|
||脏读|不可重复读|幻读|
|READ UNCOMMITTED（读未提交）|可能发生|可能发生|可能发生|
|READ COMMITTED（读已提交）|避免|可能发生|可能发生|
|REPEATABLE READ（可重复读）|避免|避免|可能发生|
|SERIALIZABLE（串行化）|避免|避免|避免|

可以在事务中使用上述语句来设置隔离级别，也可以在启动连接时通过 SET TRANSACTION ISOLATION LEVEL ... 语句设置默认隔离级别。在 MySQL 中，事务的隔离级别是可以动态设置的，而不需要重新启动数据库。

需要注意的是，随着隔离级别的提高，虽然可以提供更高的数据一致性，但也可能带来性能的降低和并发性的下降。因此，在选择隔离级别时，需要根据具体应用场景和性能需求进行权衡。默认情况下，MySQL 使用的是 REPEATABLE READ 隔离级别。

Mysql事务隔离级别的实现

MySQL 通过使用多版本并发控制（Multi-Version Concurrency Control，MVCC）来实现事务隔离级别。MVCC 是一种在并发事务访问数据库时保持一致性的技术，它通过在数据库中维护不同版本的数据来实现。

以下是 MySQL 中事务隔离级别的实现细节：

1. READ UNCOMMITTED（读未提交）：

- 事务可以读取其他事务尚未提交的数据。
- MySQL 使用快照读（Snapshot Read）来实现，允许事务读取当前事务开始前数据库状态的一个快照。

- READ COMMITTED（读已提交）：

- 事务只能读取已提交的数据。
- MySQL 通过在每个数据行中保存事务ID和提交ID来实现。事务只能看到已提交事务的数据。

- REPEATABLE READ（可重复读）：

- 事务在其生命周期内能够看到一致的数据视图，即事务开始时的数据状态。
- MySQL 使用快照读，通过保存每个事务的开始时间戳来实现。

- SERIALIZABLE（串行化）：

- 事务被强制为串行执行，避免并发访问。
- MySQL 使用锁定来实现，包括共享锁和排他锁，以确保事务的串行执行。

MVCC 在 MySQL 中的实现方式主要涉及版本号、回滚段和 Undo 日志：

- 版本号（Transaction ID）：

- 每个事务都有一个唯一的事务 ID。在每个数据行上，都会存储该数据行版本的事务 ID。通过比较事务的开始时间戳和数据行版本的事务 ID，MySQL 可以确定事务是否能够看到该数据行。

- 回滚段（Rollback Segment）：

- 用于存储事务的回滚信息。在执行更新或删除操作时，MySQL 将原始数据保存在回滚段中，以便在事务回滚时恢复数据。

- Undo 日志：

- 记录了每个事务对数据所做的更改。在事务回滚时，MySQL 会使用 Undo 日志来撤销事务的更改。

MySQL 的 MVCC 实现为数据库提供了高度的并发性和一致性。在允许多个事务同时进行读写的同时，通过版本控制来保持数据的一致性，以满足不同隔离级别的要求。

MySQL 的 MVCC 实现主要涉及版本号和回滚段。以下是更详细的实现细节：

1. 版本号（Transaction ID）：

- 每个事务都有一个唯一的事务 ID，称为 Transaction ID。这个 ID 是一个递增的整数，由系统自动生成。
- 对于每一条数据行，InnoDB 存储了两个隐藏的字段，即 DB_TRX_ID、DB_ROLL_PTR、DB_TRX_ID 、DB_ROLL_PTR 

2. 回滚段（Rollback Segment）：

- 回滚段是用于存储事务回滚信息的区域。当一个事务执行更新或删除操作时，InnoDB 会在回滚段中创建一个 Undo 日志记录，记录了更新前的数据。
- 如果事务需要回滚，可以使用 Undo 日志记录中的信息将数据还原到事务开始之前的状态。

3. Undo 日志：

- Undo 日志是用于撤销事务更改的记录。当事务执行插入、更新或删除操作时，InnoDB 会将相应的 Undo 日志写入回滚段。
- Undo 日志中包含了对数据的逆向操作，以便在事务回滚时能够正确还原数据。

如何回滚已修改并commit的数据

1. 使用事务回滚

如果你在一个事务中执行了一系列的 SQL 操作，并且还没有执行 COMMIT，那么你可以通过执行 ROLLBACK 来撤销事务的所有更改。

ROLLBACK;

这会将事务中的所有更改回滚，包括插入、更新和删除的操作。

2. 使用 Flashback 技术（仅适用于 InnoDB 存储引擎）

如果你使用的是 InnoDB 存储引擎，MySQL 并没有直接提供回滚到某个时间点的命令，但你可以使用 Flashback 技术来模拟此效果。Flashback 依赖于 MySQL 的 Undo 日志。

1. 查找事务 ID：

使用以下语句查找你想要回滚到的时间点的事务 ID：

SELECT*FROM information_schema.innodb_trx WHERE trx_started <='your_timestamp'ORDERBY trx_started DESC LIMIT 1;

2. 执行 Flashback：

使用找到的事务 ID 执行 Flashback 操作：

sqlCopy codeSET @@GLOBAL.innodb_undo_logs =1;SET @@SESSION.innodb_undo_logs =1;SET @@SESSION.innodb_undo_tablespaces ='your_undo_tablespace'; FLASHBACK TABLE your_table TO TRX_ID your_trx_id;

替换 your_table 和 your_trx_id 为实际的表名和事务 ID。
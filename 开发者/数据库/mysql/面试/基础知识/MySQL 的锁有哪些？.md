MySQL 的锁主要有以下几种：

### 1. **全局锁**

全局锁是 MySQL 中最大的锁，它会锁住整个数据库实例，阻止所有对数据库的访问。全局锁通常用于执行数据库维护操作，例如备份和恢复。
###### 1. 使用FLUSH TABLES WITH READ LOCK命令
	FLUSH TABLES WITH READ LOCK;
	该命令会将所有表置于只读状态，阻止所有对表的更新操作。但是，仍然允许对表进行查询操作。
###### 2. 设置global_read_only变量
SET GLOBAL global_read_only = 1;
该命令会将数据库置于只读状态，阻止所有对数据库的更新操作，包括对表的更新操作和对数据库结构的更改。
释放MySQL全局锁的方法有两种:
	1. 使用UNLOCK TABLES命令
	UNLOCK TABLES;
	2. 设置global_read_only变量为0
	SET GLOBAL global_read_only = 0;
### 2. 表锁

表锁会锁住整个表，阻止对表的所有读写操作。表锁通常用于执行对整个表的操作，例如 ALTER TABLE 和TRUNCATE TABLE。
###### 1. 使用LOCK TABLES命令
LOCK TABLES table_name [READ | WRITE];
该命令会将指定的表置于只读或只写状态，阻止对表的其他读写操作。
- READ: 将表置于只读状态，允许对表进行查询操作，但是阻止对表的更新操作。
- WRITE: 将表置于只写状态，允许对表进行更新操作，但是阻止对表的查询操作。
###### 1. 使用UNLOCK TABLES命令
	UNLOCK TABLES;
	
### 3. 行锁
行锁会锁住特定的数据行，阻止对该行的其他读写操作。行锁通常用于执行对特定数据的操作，例如 UPDATE 和DELETE。
###### 1. 使用SELECT...FOR UPDATE语句
	SELECT * FROM table_name WHERE condition FOR UPDATE
###### 2. 使用UPDATE或DELETE语句的WHERE子句
	UPDATE table_name SET column_name = value WHERE condition;
	DELETE FROM table_name WHERE condition;

如果UPDATE或DELETE语句的WHERE子句包含索引，MySQL会自动对满足条件的数据行加锁。
释放MySQL行锁的方法有两种:
	1. 隐式解锁
		当事务结束时，MySQL会自动释放所有行锁。
	1. 使用COMMIT或ROLLBACK语句

### 4. 页锁

页锁是 MySQL 中最小的锁，它会锁住数据库页，阻止对该页的所有读写操作。页锁通常用于内部优化，例如索引维护。

MySQL页锁的使用是自动的，用户无法直接控制。 但是，可以通过一些方法来影响页锁的使用，例如：
- 设置innodb_page_size参数
	innodb_page_size参数决定了数据库页的大小，页大小越大，页锁的粒度就越大。
	
- 设置innodb_flush_log_at_trx_commit参数
	innodb_flush_log_at_trx_commit参数决定了事务日志的刷新频率，刷新频率越低，页锁的使用就越频繁。
	
- 使用OPTIMIZE TABLE语句
	OPTIMIZE TABLE语句会重建表的索引，这可能会导致页锁的使用。
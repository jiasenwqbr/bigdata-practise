##  DDL（Data Definition Language）数据定义
### 数据库（database）

#### 创建数据库

1) 语法
```sql
CREATE DATABASE [IF NOT EXISTS] database_name
[COMMENT database_comment]
[LOCATION hdfs_path]
[WITH DBPROPERTIES (property_name=property_value, ...)];
```
2) 案例

（1）创建一个数据库，不指定路径

```sql
create database db_hive1;
```

注：若不指定路径，其默认路径为${hive.metastore.warehouse.dir}/database_name.db

（2）创建一个数据库，指定路径

```sql
create database db_hive2 location '/db_hive2';
```

（2）创建一个数据库，带有dbproperties

```sql
create database db_hive3 with dbproperties('create_date'='2022-11-18');
```

#### 查询数据库

1）展示所有数据库

（1）语法

```sql
SHOW DATABASES [LIKE 'identifier_with_wildcards'];
```

注：like通配表达式说明：*表示任意个任意字符，|表示或的关系。

（2）案例

```sql
show databases like 'db_hive*';
```

2）查看数据库信息

(1) 语法

```sql
DESCRIBE DATABASE [EXTENDED] db_name;
```

（2）案例

查看基本信息

```sql
desc database db_hive3;
```



查看更多信息

```sql
desc database extended db_hive3;
```

#### 修改数据库

用户可以使用alter database命令修改数据库某些信息，其中能够修改的信息包括dbproperties、location、owner user。需要注意的是：修改数据库location，不会改变当前已有表的路径信息，而只是改变后续创建的新表的默认的父目录。

1）语法

```sql
--修改dbproperties
ALTER DATABASE database_name SET DBPROPERTIES (property_name=property_value, ...);

--修改location
ALTER DATABASE database_name SET LOCATION hdfs_path;

--修改owner user
ALTER DATABASE database_name SET OWNER USER user_name;
```

#### 删除数据库

1）语法

```sql
DROP DATABASE [IF EXISTS] database_name [RESTRICT|CASCADE];
```

注：RESTRICT：严格模式，若数据库不为空，则会删除失败，默认为该模式。

  CASCADE：级联模式，若数据库不为空，则会将库中的表一并删除。

2）案例

（1）删除空数据库

```sql
drop database db_hive2;
```

（2）删除非空数据库

```sql
drop database db_hive3 cascade;
```



#### 切换当前数据库

语法

```sql
USE database_name;
```

### 表

#### 创建表

语法

1)普通建表

```sql
CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.]table_name   
[(col_name data_type [COMMENT col_comment], ...)]
[COMMENT table_comment]
[PARTITIONED BY (col_name data_type [COMMENT col_comment], ...)]
[CLUSTERED BY (col_name, col_name, ...) 
[SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS]
[ROW FORMAT row_format] 
[STORED AS file_format]
[LOCATION hdfs_path]
[TBLPROPERTIES (property_name=property_value, ...)]
```



1 TEMPORARY

临时表，该表只在当前会话可见，会话结束，表会被删除。

2 EXTERNAL

外部表，与之相对应的是内部表（管理表）。管理表意味着Hive会完全接管该表，包括元数据和HDFS中的数据。而外部表则意味着Hive只接管元数据，而不完全接管HDFS中的数据。

3 data_type

Hive中的字段类型可分为基本数据类型和复杂数据类型。

基本数据类型如下：

| Hive      | 说明                                                | 定义          |
| --------- | --------------------------------------------------- | ------------- |
| tinyint   | 1byte有符号整数                                     |               |
| smallint  | 2byte有符号整数                                     |               |
| int       | 4byte有符号整数                                     |               |
| bigint    | 8byte有符号整数                                     |               |
| boolean   | 布尔类型，true或者false                             |               |
| float     | 单精度浮点数                                        |               |
| double    | 双精度浮点数                                        |               |
| decimal   | 十进制精准数字类型                                  | decimal(16,2) |
| varchar   | 字符序列，需指定最大长度，最大长度的范围是[1,65535] | varchar(32)   |
| string    | 字符串，无需指定最大长度                            |               |
| timestamp | 时间类型                                            |               |
| binary    | 二进制数据                                          |               |

复杂数据类型如下；

| 类型   | 说明                                                     | 定义                        | 取值       |
| ------ | -------------------------------------------------------- | --------------------------- | ---------- |
| array  | 数组是一组相同类型的值的集合                             | array<string>               | arr[0]     |
| map    | map是一组相同类型的键-值对集合                           | map<string, int>            | map['key'] |
| struct | 结构体由多个属性组成，每个属性都有自己的属性名和数据类型 | struct<id:int, name:string> | struct.id  |

注：类型转换

Hive的基本数据类型可以做类型转换，转换的方式包括隐式转换以及显示转换。

方式一：隐式转换

具体规则如下：

a. 任何整数类型都可以隐式地转换为一个范围更广的类型，如tinyint可以转换成int，int可以转换成bigint。

b. 所有整数类型、float和string类型都可以隐式地转换成double。

c. tinyint、smallint、int都可以转换为float。

d. boolean类型不可以转换为任何其它的类型。

详情可参考Hive官方说明：[Allowed Implicit Conversions](#LanguageManualTypes-AllowedImplicitConversions)

方式二：显示转换

可以借助cast函数完成显示的类型转换

a.语法

```sql
cast(expr as <type>) 
```



b.案例

```sql
select '1' + 2, cast('1' as int) + 2;
```



4 PARTITIONED BY

创建分区表

5 CLUSTERED BY ... SORTED BY...INTO ... BUCKETS

创建分桶表

6 ROW FORMAT

指定SERDE，SERDE是Serializer and Deserializer的简写。Hive使用SERDE序列化和反序列化每行数据。详情可参考 [Hive-Serde](#DeveloperGuide-HiveSerDe)。语法说明如下：

***\*语法一：\****DELIMITED关键字表示对文件中的每个字段按照特定分割符进行分割，其会使用默认的SERDE对每行数据进行序列化和反序列化。

```sql
ROW FORAMT DELIMITED 
[FIELDS TERMINATED BY char] 
[COLLECTION ITEMS TERMINATED BY char] 
[MAP KEYS TERMINATED BY char] 
[LINES TERMINATED BY char] 
[NULL DEFINED AS char]
```

注：

fields terminated by ：列分隔符
collection items terminated by ： map、struct和array中每个元素之间的分隔符
map keys terminated by ：map中的key与value的分隔符
lines terminated by ：行分隔符

语法二：SERDE关键字可用于指定其他内置的SERDE或者用户自定义的SERDE。例如JSON SERDE，可用于处理JSON字符串。

```sql
ROW FORMAT SERDE serde_name [WITH SERDEPROPERTIES (property_name=property_value,property_name=property_value, ...)]
```



7 STORED AS
指定文件格式，常用的文件格式有，textfile（默认值），sequence file，orc file、parquet file等等。
8 LOCATION
指定表所对应的HDFS路径，若不指定路径，其默认值为
${hive.metastore.warehouse.dir}/db_name.db/table_name
9 TBLPROPERTIES
用于配置表的一些KV键值对参数

2）Create Table As Select（CTAS）建表
该语法允许用户利用select查询语句返回的结果，直接建表，表的结构和查询语句的结构保持一致，且保证包含select查询语句放回的内容。

```sql
CREATE [TEMPORARY] TABLE [IF NOT EXISTS] table_name 
[COMMENT table_comment] 
[ROW FORMAT row_format] 
[STORED AS file_format] 
[LOCATION hdfs_path]
[TBLPROPERTIES (property_name=property_value, ...)]
[AS select_statement]
```

3）Create Table Like语法
该语法允许用户复刻一张已经存在的表结构，与上述的CTAS语法不同，该语法创建出来的表中不包含数据。3）Create Table Like语法
该语法允许用户复刻一张已经存在的表结构，与上述的CTAS语法不同，该语法创建出来的表中不包含数据。

```sql
CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.]table_name
[LIKE exist_table_name]
[ROW FORMAT row_format] 
[STORED AS file_format] 
[LOCATION hdfs_path]
[TBLPROPERTIES (property_name=property_value, ...)]
```



#### 查看表

1）展示所有表

（1）语法

```sql
SHOW TABLES [IN database_name] LIKE ['identifier_with_wildcards'];
```

注：like通配表达式说明：*表示任意个任意字符，|表示或的关系。

（2）案例

```sql
show tables like 'stu*';
```



2）查看表信息

（1）语法

```sql
DESCRIBE [EXTENDED | FORMATTED] [db_name.]table_name
```

注：EXTENDED：展示详细信息
	FORMATTED：对详细信息进行格式化的展示

查看表信息

```sql
desc stu;
```

查看更多信息

```sql
desc formatted stu;
```

#### 修改表

1）重命名表

（1）语法

```sql
ALTER TABLE table_name RENAME TO new_table_name
```

（2）案例

```sql
alter table stu rename to stu1;
```



2）修改列信息

（1）语法

增加列

​	该语句允许用户增加新的列，新增列的位置位于末尾。

```sql
ALTER TABLE table_name ADD COLUMNS (col_name data_type [COMMENT col_comment], ...)
```

更新列

​	该语句允许用户修改指定列的列名、数据类型、注释信息以及在表中的位置。

```sql
ALTER TABLE table_name CHANGE [COLUMN] col_old_name col_new_name column_type [COMMENT col_comment] [FIRST|AFTER column_name]
```

替换列

​	该语句允许用户用新的列集替换表中原有的全部列

```sql
ALTER TABLE table_name REPLACE COLUMNS (col_name data_type [COMMENT col_comment], ...)
```

（2）案例

查询表结构

```sql
desc stu;
```

添加列

```sql
alter table stu add columns(age int);
```

更新列

```sql
 alter table stu change column age ages double;
```

替换列

```sql
alter table stu replace columns(id int, name string);
```

#### 删除表

1）语法

```sql
DROP TABLE [IF EXISTS] table_name;
```

2）案例

```sql
drop table stu;
```



#### 清空表

1）语法

```sql
TRUNCATE [TABLE] table_name
```

注意：truncate只能清空管理表，不能删除外部表中数据。

2）案例

```sql
truncate table student;
```



## DML（Data Manipulation Language）数据操作

### load

Load语句可将文件导入到Hive表中。

1）语法

```sql
LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename [PARTITION (partcol1=val1, partcol2=val2 ...)];
```

关键字说明：

（1）local：表示从本地加载数据到Hive表；否则从HDFS加载数据到Hive表。

（2）overwrite：表示覆盖表中已有数据，否则表示追加。

（3）partition：表示上传到指定分区，若目标是分区表，需指定分区。

### insert

1）语法

```sql
INSERT (INTO | OVERWRITE) TABLE tablename [PARTITION (partcol1=val1, partcol2=val2 ...)] select_statement;
```

关键字说明：

（1）INTO：将结果追加到目标表

（2）OVERWRITE：用结果覆盖原有数据



### export & import

Export导出语句可将表的数据和元数据信息一并到处的HDFS路径，Import可将Export导出的内容导入Hive，表的数据和元数据信息都会恢复。Export和Import可用于两个Hive实例之间的数据迁移。

```sql
--导出
EXPORT TABLE tablename TO 'export_target_path'

--导入
IMPORT [EXTERNAL] TABLE new_or_original_tablename FROM 'source_path' [LOCATION 'import_target_path']
```



案例：

```sql
--导出
export table default.student to '/user/hive/warehouse/export/student';

--导入
import table student2 from '/user/hive/warehouse/export/student';
```



## 查询

### 基础语法








































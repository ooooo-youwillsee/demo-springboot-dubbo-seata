# 此代码参考 [seata-samples](https://github.com/seata/seata-samples/tree/master/demo-springboot-dubbo-seata)

# SpringBoot + Dubbo + Mybatis + Nacos + Seata

Integration SpringBoot + Dubbo + Mybatis + Nacos + Seata

### 1. clone the code

- samples-common common module

- samples-account user account module

- samples-order order module

- samples-stock stock module

- samples-business business module

### 2. prepare database

create database （默认为：seata），import db_seata.sql to database

then you will see ：

```
+-------------------------+
| Tables_in_seata         |
+-------------------------+
| t_account               |
| t_order                 |
| t_stock               |
| undo_log                |
+-------------------------+
```

### 3. start Nacos Server

```bash
docker run -d --name nacos-server -p 8848:8848 -p 9848:9848 nacos/nacos-server:v2.2.3
```

enter the Nacos web console：http://127.0.0.1:8848/nacos/index.html

### 4. start Seata Server


```bash
docker run -d --name seata-server -p 8091:8091 -p 7091:7091 seataio/seata-server:1.8.0
```

enter the Seata web console: http://localhost:7091/#/transaction/list

### 5. start the demo module

start samples-account、samples-order、samples-stock、samples-business

use Nacos web console to ensure the registry is ok: http://127.0.0.1:8848/nacos/#/serviceManagement

> check the datasource config in application.properties is right.

### 6. start the normal request

use postman to send a post request：http://localhost:8104/business/dubbo/buy

body：

```json
{
    "userId":"1",
    "commodityCode":"C201901140001",
    "name":"fan",
    "count":2,
    "amount":"100"
}
```

or use curl：

```bash
curl -H "Content-Type:application/json" -X POST -d '{"userId":"1","commodityCode":"C201901140001","name":"风扇","count":2,"amount":"100"}' localhost:8104/business/dubbo/buy
``` 

then this will send a pay request,and return code is 200

### 7. test the rollback request

enter samples-business , change BusinessServiceImpl, uncomment the following code ：

```
if (!flag) {
  throw new RuntimeException("测试抛异常后，分布式事务回滚！");
}
```

restart the samples-business module, and execute the step 6.

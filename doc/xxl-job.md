### 一、任务调度
#### 1、什么时候进行代码执行
#### 2、Java实现定时任务有几种
```
Thread:Thread.sleep()
TimerTask
线程池，可定时线程
quartz 定时任务框架
springboot内置定时任务框架

```
#### 3、分布式JOB解决幂等性问题
```
在分布式情况下，job在每个tomcat上都会执行，容易出现重复执行

解决？
1.使用分布式锁(zk或者redis),保证只有一台执行job (预分配需要多态机器)
2.使用配置文件(启动时命令)，配置文件中加一个开关，打开开关的执行，没打卡的不执行
3.使用数据库唯一标识
传统任务调度的缺点？
1.没有补偿机制（报错就不执行了）
2.不支持集群
3.不支持路由策略
4.统计
5.报警
6.报警、监控
```

#### 4、分布式调度原理

```

```

### XXL-JOB集成
#### 1、源码下载
https://www.xuxueli.com/xxl-job/

#### 2、初始化admin数据库

1) xxl-job-group  执行器分组

2) xxl-job-info  任务信息

3) xxl-job-lock  任务通知分布式锁

4) xxl-job-log  任务执行日志

5) xxl-job-laglue  glue模式任务日志

6) xxl-job-registry  执行器注册信息

7) xxl-user  admin用户表


#### 3、修改admin配置文件

#### 4、访问admin首页，执行器列表、任务列表
http://127.0.0.1:8081/xxl-job-admin

#### 5、执行器依耐core，修改配置文件
xxl.job.admin.addresses=http://127.0.0.1:8081/xxl-job-admin

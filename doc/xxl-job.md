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

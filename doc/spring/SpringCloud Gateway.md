### SpringCloud Gateway 特征
```
（1）基于 Spring Framework 5，Project Reactor 和 Spring Boot 2.0

（2）集成 Hystrix 断路器

（3）集成 Spring Cloud DiscoveryClient

（4）Predicates 和 Filters 作用于特定路由，易于编写的 Predicates 和 Filters

（5）具备一些网关的高级功能：动态路由、限流、路径重写

```

### 几个问题
```
什么是服务网关：
    替代netffix zuul,阻塞IO
    隔离外部访问和内部系统

为什么要使用服务网关：
    单体：浏览器、发送到应用机器、查询数据库、返回浏览器  ==> 不需要网关
    微服务: 服务很多，需要知道机器IP，复杂，直接把请求标识判断具体微服务地址，再把请求发送到微服务实例
    
    
解决了什么问题
```

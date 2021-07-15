### Feign声明式服务调用
#### 1.什么是Feign
```
封装了Ribbon和RestTemplate
为了更方便使用Springcloud孵化了OpenFeign
```
#### 2.解决了什么问题
```
面向接口远程调用；提供无感知的远程调用
```
#### 3.Feign和OpenFeign
```
OpenFeign是Springcloud在Feign的基础上支持了Springmvc的注解：@RequestMapping、@Pathvariable

OpenFeign的@FeignClient可以解析SpringMVC的@RequestMapping注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用服务。
```
#### 4.Feign性能优化
#### 5.Feign请求传参
#### 6.Feign负载均衡
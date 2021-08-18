```

https://blog.csdn.net/xander_lu/article/details/108776020

https://hellowoodes.blog.csdn.net/article/details/100635405

https://blog.csdn.net/qq_43280818/article/details/107164860

```


#### 注册 
```
k8s服务部署完成后,自动完成了服务注册,不需要任何第三方组件,既一个普通的spring boot项目部署到k8s中就已经自动的完成了服务注册,信息存储在etcd中.
```

#### 发现
```
发现:spring cloud kubernetes封装了对k8s apiserver的请求,可获取到服务列表和实例信息,但个人认为该模块一般用不到.

openfeign模块以声明式注解的方式实现了http client,并会自动和ribbo融合(不配置url的时候,必须导入ribbon模块依赖)
    
    1、配置url属性：只要配置了url为k8s中服务提供者的service,就可以访问,跟是否在k8s中没有区别
    
    2.配置name属性：当配置name时,依赖ribbon模块,spring cloud kubernetes ribbon模块负责从k8s的service中获取pods的信息,对openfeign封装负载均衡的信息

```

#### spring cloud kubernetes
```
封装了跟api Servier的http交互

方便项目中对api Server的请求和读取k8s中的ConfigMaps和Secrets

pring-cloud-kubernetes提供的注册发现能力，以轮询的方式访问指定服务的全部pod
```
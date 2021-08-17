https://www.baeldung.com/spring-cloud-kubernetes#overview
### 概述
- 安装Minikube 
- 使用Minikube在单节点群集上设置应用程序
- 使用YAML配置文件部署应用程序
- 组件
```
service discovery through Spring Cloud Kubernetes
configuration management and injecting Kubernetes ConfigMaps and secrets to application pods using Spring Cloud Kubernetes Config
load balancing using Spring Cloud Kubernetes Ribbon
Spring cloud k8s  ==>   服务发现

Spring Cloud Kubernetes Config  ==> 配置管理、k8s映射表、密码

Spring Cloud Kubernetes Ribbon  ==> 负载均衡
```


### 环境安装
```
安装Minikube ==> install Minikube on our local machine
minikube start --vm-driver=virtualbox
kubectl config use-context minikube
minikube dashboard
```

### Deployment




### Service Discovery
```
https://github.com/eugenp/tutorials/tree/master/spring-cloud/spring-cloud-kubernetes
Kubernetes将服务公开为端点的集合，这些端点可以从运行在同一Kubernetes集群中的pod中的Spring Boot应用程序中获取和访问。

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-kubernetes</artifactId>
</dependency>

@SpringBootApplication
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


@RestController
public class ClientController {
    @Autowired
    private DiscoveryClient discoveryClient;
}



apiVersion: v1 by d
kind: ConfigMap
metadata:
  name: client-service
data:
  application.properties: |-
    bean.message=Testing reload! Message from backend is: %s <br/> Services : %s
    
    
kubectl create -f client-config.yaml



@Configuration
@ConfigurationProperties(prefix = "bean")
public class ClientConfig {

    private String message = "Message from backend is: %s <br/> Services : %s";

    // getters and setters
}


@RestController
public class ClientController {

    @Autowired
    private ClientConfig config;

    @GetMapping
    public String load() {
        return String.format(config.getMessage(), "", "");
    }
}

If we don't specify a ConfigMap, then we should expect to see the default message, which is set in the class. 
However, when we create the ConfigMap, this default message gets overridden by that property.
```

### Secrets
```
apiVersion: v1
kind: Secret
metadata:
  name: db-secret
data:
  username: dXNlcg==
  password: cDQ1NXcwcmQ=
  
  
kubectl apply -f secret.yaml


apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: mongo
spec:
  replicas: 1
  template:
    metadata:
      labels:
        service: mongo
      name: mongodb-service
    spec:
      containers:
      - args:
        - mongod
        - --smallfiles
        image: mongo:latest
        name: mongo
        env:
          - name: MONGO_INITDB_ROOT_USERNAME
            valueFrom:
              secretKeyRef:
                name: db-secret
                key: username
          - name: MONGO_INITDB_ROOT_PASSWORD
            valueFrom:
              secretKeyRef:
                name: db-secret
                key: password


Communication with Ribbon
automatically discover and reach all the endpoints of a specific service

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-kubernetes-ribbon</artifactId>
</dependency>

@RibbonClient(name = "travel-agency-service")

ribbon.http.client.enabled=true





```
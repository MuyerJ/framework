
https://blog.51cto.com/u_14902238/2564320
### 一、创建ASK集群
#### 1.登录管理后台：https://cs.console.aliyun.com/?spm=a2c4g.11186623.2.6.1f9929dbBAzUJi#/k8s/cluster/list
#### 2.在控制台左侧导航栏中，单击集群。
#### 3.在集群列表页面中，单击页面右上角的创建集群。
#### 4.单击ASK集群页签，然后完成集群配置
#### 5.创建成功后,可以在kubernetes 集群列表看到创建的集群

###  二、创建ASK的在线应用
#### 1.通过kubectl
```
步骤一：安装kubectl工具
步骤二：选择集群凭证类型
    1.登录容器服务管理控制台。
    2.在控制台左侧导航栏中，单击集群。
    3.在集群列表页面中，单击目标集群名称或者目标集群右侧操作列下的详情。
    4.在集群信息页面，单击连接信息页签，根据需要选择公网或私网访问凭证。
步骤三：配置集群凭证
kubectl工具默认会从客户端机器的$HOME/.kube目录下查找名为config的文件，该文件用于存储所要管理集群的访问凭证，kubectl会根据该配置文件连接至集群。
    1.选择公网访问或私网访问页签，单击复制
    2.将复制的集群凭证内容粘贴至$HOME/.kube目录下的config文件中，保存并退出。
        如果 $HOME/目录下没有 .kube目录和 config文件，请自行创建。



步骤四：验证集群连通性

kubectl get namespace
NAME              STATUS   AGE
default           Active   4h39m
kube-node-lease   Active   4h39m
kube-public       Active   4h39m
kube-system       Active   4h39m

可以 生成临时的KubeConfig 详情查看 
    [https://help.aliyun.com/document_detail/86494.htm?spm=a2c4g.11186623.2.6.1e0c7887SqTHMt#task-ubf-lhg-vdb]

```
#### 2.使用以下样例创建名为nginx.yaml的YAML文件

```
创建名为nginx.yaml的YAML文件
apiVersion: v1
kind: Service
metadata:
  name: nginx-service
spec:
  ports:
  - port: 80
    protocol: TCP
  selector:
    app: nginx
  type: LoadBalancer


执行 [ kubectl apply -f nginx.yaml ] 部署应用
预期输出：
service/nginx-service created
deployment.apps/nginx-deploy created


查看Pod和Serivce状态，并通过SLB IP访问Nginx应用。
kubectl get pod
nginx-deploy-55d8dcf755-bxk8n   1/1     Running   0          36s
nginx-deploy-55d8dcf755-rchhq   1/1     Running   0          36s

kubectl get svc
NAME            TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
kubernetes      ClusterIP      172.**.*.*     <none>        443/TCP        10d
nginx-service   LoadBalancer   172.19.*.***   47.57.**.**   80:32278/TCP   39s

curl 47.57.**.**
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
...
</html>


扩容Deployment。
kubectl get deploy
NAME           READY   UP-TO-DATE   AVAILABLE   AGE
nginx-deploy   2/2     2            2           9m32s

kubectl scale deploy nginx-deploy --replicas=10
deployment.extensions/nginx-deploy scaled

kubectl get pod
10个...


应用副本缩容
kubectl get pod
NAME                            READY   STATUS    RESTARTS   AGE
nginx-deploy-55d8dcf755-rchhq   1/1     Running   0          16m

kubectl autoscale deployment nginx-deploy --cpu-percent=50 --min=1 --max=10
horizontalpodautoscaler.autoscaling/nginx-deploy autoscaled

kubectl get hpa

```
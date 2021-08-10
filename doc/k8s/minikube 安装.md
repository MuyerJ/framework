可参考文档
```
https://blog.csdn.net/weixin_40449300/article/details/100110560
https://blog.csdn.net/weixin_43695104/article/details/100703437
https://www.cnblogs.com/victorfrost/p/12563589.html
https://www.cnblogs.com/digdeep/p/12319340.html
https://blog.csdn.net/lileihappy/article/details/80444192
https://zhuanlan.zhihu.com/p/85810571
https://blog.csdn.net/aixiaoyang168/article/details/78331847
https://www.cnblogs.com/zkwzkw/p/13509160.html  [好文]
```
#### 步骤

##### step1.下载镜像并授权
```
国外镜像：
    curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && chmod +x minikube
    sudo cp minikube /usr/local/bin && rm minikube
国内镜像：
    wget -nc "https://coding-public-generic.pkg.coding.net/public/downloads/kubectl-linux-amd64?version=v1.21.0" -O kubectl-linux-amd64-v1.21.0 | true
    cp kubectl-linux-amd64-v1.21.0 /usr/local/bin/kubectl
    chmod +x /usr/local/bin/kubectl
    kubectl version --client
```

##### step2.启动minikube
```
minikube start --driver=docker

[yejiang@centos1 bin]$ minikube start --driver=docker
* Centos 7.5.1804 上的 minikube v1.22.0
* 根据用户配置使用 docker 驱动程序
* Starting control plane node minikube in cluster minikube
* Pulling base image ...
* Downloading Kubernetes v1.21.2 preload ...
    > preloaded-images-k8s-v11-v1...: 502.14 MiB / 502.14 MiB  100.00% 2.42 MiB
    > index.docker.io/kicbase/sta...: 361.08 MiB / 361.09 MiB  100.00% 1.85 MiB
! minikube was unable to download gcr.io/k8s-minikube/kicbase:v0.0.25, but successfully downloaded kicbase/stable:v0.0.25 as a fallback image
* Creating docker container (CPUs=2, Memory=2200MB) ...
! This container is having trouble accessing https://k8s.gcr.io
* To pull new external images, you may need to configure a proxy: https://minikube.sigs.k8s.io/docs/reference/networking/proxy/
* 正在 Docker 20.10.7 中准备 Kubernetes v1.21.2…
  - Generating certificates and keys ...
  - Booting up control plane ...
  - Configuring RBAC rules ...
* Verifying Kubernetes components...
  - Using image gcr.io/k8s-minikube/storage-provisioner:v5
* Enabled addons: default-storageclass, storage-provisioner
* kubectl not found. If you need it, try: 'minikube kubectl -- get pods -A'
* Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default



按照日志提示

问题：【minikube was unable to download gcr.io/k8s-minikube/kicbase:v0.0.25, but successfully downloaded kicbase/stable:v0.0.25 as a fallback image】
https://blog.csdn.net/kelsel/article/details/107728562

问题：【 kubectl not found. If you need it, try: 'minikube kubectl -- get pods -A'】
minikube kubectl -- get pods -A
$ minikube kubectl -- get pods -A
    > kubectl.sha256: 64 B / 64 B [--------------------------] 100.00% ? p/s 0s
    > kubectl: 44.27 MiB / 44.27 MiB [--------------] 100.00% 7.16 MiB p/s 6.4s
NAMESPACE     NAME                               READY   STATUS    RESTARTS   AGE
kube-system   coredns-558bd4d5db-6lwkz           1/1     Running   0          4m56s
kube-system   etcd-minikube                      1/1     Running   0          5m4s
kube-system   kube-apiserver-minikube            1/1     Running   0          5m8s
kube-system   kube-controller-manager-minikube   1/1     Running   0          5m8s
kube-system   kube-proxy-cc9rg                   1/1     Running   0          4m56s
kube-system   kube-scheduler-minikube            1/1     Running   0          5m4s
kube-system   storage-provisioner                1/1     Running   1          5m2s



$ minikube status
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured

问题：【This container is having trouble accessing https://k8s.gcr.io】

minikube start --image-repository=registry.cn-hangzhou.aliyuncs.com/google_containers




$ minikube dashboard
* 正在开启 dashboard ...
  - Using image kubernetesui/dashboard:v2.1.0
  - Using image kubernetesui/metrics-scraper:v1.0.4
* 正在验证 dashboard 运行情况 ...
* Launching proxy ...
* 正在验证 proxy 运行状况 ...
* Opening http://127.0.0.1:44623/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/ in your default browser...
START /usr/bin/firefox "http://127.0.0.1:44623/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/"
Error: GDK_BACKEND does not match available displays
xdg-open: no method available for opening 'http://127.0.0.1:44623/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/'

X Exiting due to HOST_BROWSER: failed to open browser: exit status 3


```
##### stop3.运行一个服务和暴露一个服务
```
文章：
https://blog.csdn.net/weixin_30405421/article/details/98461538
===================================


kubectl create delpoyment nginx  --image=nginx --port=8083
kubectl run nginx --image=nginx --port=8082
===================================

kubectl get deployments

===================================
[yejiang@centos1 bin]$ kubectl run hello-minikube --image=registry.cn-qingdao.aliyuncs.com/k8slast/echoserver --port=8080
pod/hello-minikube created

[yejiang@centos1 bin]$ kubectl expose deployment hello-minikube --type=NodePort
Error from server (NotFound): deployments.apps "hello-minikube" not found

[yejiang@centos1 bin]$ kubectl get pods
NAME             READY   STATUS             RESTARTS   AGE
hello-minikube   0/1     ImagePullBackOff   0          10m



kubectl run hello-world1 --image=hello-world --port=8081
```
##### stop4.查看服务
```
[yejiang@centos1 bin]$ kubectl get services
NAME         TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)        AGE
kubernetes   ClusterIP      10.96.0.1        <none>        443/TCP        17h
nginx        LoadBalancer   10.103.173.239   <pending>     80:30586/TCP   60m

```
##### stop5.删除一个服务
```
kubectl delete pods <pod> --grace-period=0 --force
```

#### 坑
- X Exiting due to DRV_AS_ROOT: The “docker“ driver should not be used with root privileges
```
https://blog.csdn.net/fly_leopard/article/details/108790217
```

- pods的status 是Completed
```
$ kubectl get pods
NAME           READY   STATUS      RESTARTS   AGE
hello-world1   0/1     Completed   3          9h
nginx          1/1     Running     0          9h


如何解决？
https://blog.51cto.com/shunzi115/2449411?source=dra
https://blog.csdn.net/Wuli_SmBug/article/details/103767136
```
- kubernetes 部署 nginx ，使用 kubectl get deployment 时出现 No resources found in default
```
https://www.cnblogs.com/zkwzkw/p/13509160.html
kubectl create deployment nginx --image=nginx

minikube service nginx --url

```
- 如果存在网络问题，请使用--image-repository=registry.cn-hangzhou.aliyuncs.com/google_containers指定镜像仓库地址。

```

[yejiang@centos1 bin]$ kubectl get nodes
NAME       STATUS   ROLES                  AGE   VERSION
minikube   Ready    control-plane,master   16h   v1.21.2

[yejiang@centos1 bin]$ kubectl get pod
NAME           READY   STATUS             RESTARTS   AGE
hello-world1   0/1     CrashLoopBackOff   13         9h
nginx          1/1     Running            0          9h


[yejiang@centos1 bin]$ kubectl get services
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   16h

pod is the smallest unit , but you are creating
deployment - abstraction over pods


```




-----


sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://1jx5a5cs.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker


[yejiang@centos1 bin]$ kubectl version
Client Version: version.Info{Major:"1", Minor:"21", GitVersion:"v1.21.0", GitCommit:"cb303e613a121a29364f75cc67d3d580833a7479", GitTreeState:"clean", BuildDate:"2021-04-08T16:31:21Z", GoVersion:"go1.16.1", Compiler:"gc", Platform:"linux/amd64"}
Server Version: version.Info{Major:"1", Minor:"21", GitVersion:"v1.21.2", GitCommit:"092fbfbf53427de67cac1e9fa54aaa09a28371d7", GitTreeState:"clean", BuildDate:"2021-06-16T12:53:14Z", GoVersion:"go1.16.5", Compiler:"gc", Platform:"linux/amd64"}
[yejiang@centos1 bin]$  docker version
Client: Docker Engine - Community
 Version:           20.10.8
 API version:       1.41
 Go version:        go1.16.6
 Git commit:        3967b7d
 Built:             Fri Jul 30 19:55:49 2021
 OS/Arch:           linux/amd64
 Context:           default
 Experimental:      true

Server: Docker Engine - Community
 Engine:
  Version:          20.10.8
  API version:      1.41 (minimum version 1.12)
  Go version:       go1.16.6
  Git commit:       75249d8
  Built:            Fri Jul 30 19:54:13 2021
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          1.4.9
  GitCommit:        e25210fe30a0a703442421b0f60afac609f950a3
 runc:
  Version:          1.0.1
  GitCommit:        v1.0.1-0-g4144b63
 docker-init:
  Version:          0.19.0
  GitCommit:        de40ad0

-----






### 删除命令
```
kubectl get pods
kubectl delete pod <podname> -n <namespace>

kubectl get deployment -n <namespace>
kubectl delete deployment <deployment名> -n <namespace>
```

### 命令：kubectl describe pod

```
Name:         nginx-6799fc88d8-8x7mj
Namespace:    default
Priority:     0
Node:         minikube/192.168.49.2
Start Time:   Wed, 11 Aug 2021 10:31:38 +0800
Labels:       app=nginx
              pod-template-hash=6799fc88d8
Annotations:  <none>
Status:       Running
IP:           172.17.0.8
IPs:
  IP:           172.17.0.8
Controlled By:  ReplicaSet/nginx-6799fc88d8
Containers:
  nginx:
    Container ID:   docker://fbb6b6e23d5ea5206f6d72e96cb8bf94950b5003730cd84d6d3561419d0f7c6c
    Image:          nginx
    Image ID:       docker-pullable://nginx@sha256:8f335768880da6baf72b70c701002b45f4932acae8d574dedfddaf967fc3ac90
    Port:           <none>
    Host Port:      <none>
    State:          Running
      Started:      Wed, 11 Aug 2021 10:31:44 +0800
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-7bbzs (ro)
Conditions:
  Type              Status
  Initialized       True 
  Ready             True 
  ContainersReady   True 
  PodScheduled      True 
Volumes:
  kube-api-access-7bbzs:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    ConfigMapOptional:       <nil>
    DownwardAPI:             true
QoS Class:                   BestEffort
Node-Selectors:              <none>
Tolerations:                 node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:                      <none>

```
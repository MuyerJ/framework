```
kubectl apply  -f ../k8s/rbac-cluster-role.yaml
---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: default
  name: microservices-kubernetes-namespace-reader
rules:
  - apiGroups: [""] # "" indicates the core API group
    resources: ["configmaps", "pods", "services", "endpoints", "secrets"]
    verbs: ["get", "list", "watch"]
---
kubectl create namespace department
kubectl create serviceaccount api-service-account -n department
kubectl create clusterrolebinding service-pod-reader-department --clusterrole=microservices-kubernetes-namespace-reader --serviceaccount=api-service-account
```

kubectl create serviceaccount api-service-account -n default

kubectl create clusterrolebinding service-pod-reader-default --clusterrole=microservices-kubernetes-namespace-reader --serviceaccount=api-service-account:default
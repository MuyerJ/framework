# Docker 基础


### 一、Docker 概述

#### 1.简介

项目带上环境安装打包 ， 发布一个项目 (jar + 环境)

#### 2.Docker的历史

2010年,几个搞IT的年轻人,美国成立一家公司dotCloud。
做一些pass的云计算服务,LXC有关的容器技术
他们将自己的技术(容器化技术)命名就是Docker

Docker刚刚诞生的时候,还没引起行业的注意！dotCloud活不下去

开放源代码！

2013年,Docker开源!
Docker越来越多人发现docker优点！
2014年4月9月,Docker1.0发布！


为什么这么火？ 十分轻巧

虚拟机笨重。虚拟机是虚拟化技术、Docker容器技术,也是虚拟化技术

注意：
LXC：Linux Container容器是一种内核虚拟化技术，可以提供轻量级的虚拟化，以便隔离进程和资源。 

#### 3.聊聊Docker

是用Go语言开发的,开源项目

文档地址：https://docs.docker.com/ Docker的文档是超级详细的

仓库地址: https://hub.docker.com/

#### 4.Docker能干嘛

之前的虚拟机技术：
- 缺点
    资源占用十分多、冗余步骤多、启动很慢

容器化技术：
- 容器化技术不是模拟的完整的操作系统
    
容器化技术和虚拟机技术不同
- 传统虚拟机技术,虚拟一条硬件，运行一个完整的操作系统
- 容器内的应用直接运行在宿主机的内核，容器是没有自己的内核的,也没有虚拟硬件,所以轻便
- 每个容器间都是互相隔离的,每个容器内都有自己的文件系统,互不影响


#### 5.DevOps（开发、运维）


更快速的交付和部署

- 传统：一堆文档、安装程序

- Docker：打包镜像发布测试、一键运行

更便捷的升级和扩缩容

更简单的系统运维
- 在容器化之后，我们的开发,测试环境都是高度一致的

更高效的计算资源利用

- Docker是内核级别的虚拟化，可以在物理机上可以运行很多容器实例！服务器的性能可以被压榨的极致





### 二、Docker 安装


#### 1.Docker组成
- image(镜像)

镜像就好比是一个模板,通过模板可以创建容器(docker run)

通过镜像可以创建多个容器

- container(容器)

可以把容器简单理解为一个缩小版的Linux

- repository(仓库)

存放镜像的地方，仓库分为共有仓库额私有仓库，可以类比maven仓库

Docker Hub、阿里云... 等都有自己的容器服务器

#### 2.安装Docker
- 环境要求

Linux：CentOS7、内核是3.10以上

查看命令:
```
系统版本：cat /ect/os-release
内核版本：uname -r   
```
- 安装步骤
```
1.卸载旧版本

sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
                  

2.需要的安装包

sudo yum install -y yum-utils

3.设置镜像仓库(默认国外的,设置成阿里云的)

sudo yum-config-manager \
    --add-repo \
    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    
//更新yum软件包索引
yum makecache fast

4.安装docker 

sudo yum install docker-ce docker-ce-cli containerd.io

5.启动docker

sudo systemctl start docker


6.查看版本,看是否启动成功

docker version
  
```

- 卸载docker
```
1.卸载依赖
yum remove docker-ce docker-ce-cli containerd.io
2.删除资源
rm -rf /var/lib/docker
```

#### 3.docker run 的流程

```
Docker 会在本地寻找image
    如果本机找到 --> 使用这个image运行
    如果没有,去Docker hub上下载 -->
        下载成功 --> 使用下载的这个image运行
        没有下载资源 --> 报错 
```

### 三、Docker 命令

#### 1.帮助命令

```
docker version
docker info
docker 命令 --help
帮助文档地址：https://docs.docker.com/reference/
```
#### 2.镜像命令

```
docker images  -aq //查看本地机器上所有的镜像
docker search
docker pull 镜像名[:version] //pull是分层下载的,若没有指定版本默认使用最新的版本
docker rmi -f [imageid,...]|$(docker images -aq)
```

#### 3.容器命令

```
说明：我们有了镜像才会有容器

//运行容器
docker run image 
    --name="" 容器名字,用来区分多个容器
    -d        后台方式运行
    -it       使用交互方式运行,进如到容器查看内容
    -p        指定容器端口号 -p:8080:8080
    
//列出运行的容器
docker ps -a
docker ps -a -n=1
docker ps -aq

//退出容器
exit 直接停止容器并且退出
ctril+p+q  容器不停止的退出

//删除容器
docker rm 容器id
docker rm -f $(docker ps -aq)

//停止和启动容器
docker start 容器id
docker stop 容器id
docker restart 容器id
docker kill 容器id


eg:
>>>1
docker run -it centos /bin/bash
exit
```

#### 4.其他命令

- 后台启动容器
```
docker run -d 镜像名
docker run -dit 镜像名
https://blog.csdn.net/weixin_34356310/article/details/92178989
```
- 查看日志
```
docker logs -tf --tail 容器names

eg:
docker run -d centos /bin/sh -c "while true;do echo yejiang;sleep 1;done;"
```

- 查看元数据

```
docker inspect 容器id 
```

- 进入当前正在运行的容器
```
方式一：exec --> 进入容器后开启一个新的终端,可以在里面操作(常用)

docker exec -it 容器id bashShell

方式二：attach -->进入容器正在执行的中断,不会启动新的进程

docker attach 容器id
```

- 从容器中拷贝文件到主机上
```
docker cp 容器id:容器内的路径 目的主机路径
```

#### 5.命令图
![](../img/docker_command.png)


### Docker 镜像！
### Docker 容器数据卷
### DockerFile
### Docker 网络原理
### IDEA 整合 Docker
### Docker Compose
### Docker Swarm
### CI\CD Jenkins

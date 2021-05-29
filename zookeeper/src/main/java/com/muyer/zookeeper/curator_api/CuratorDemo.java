package com.muyer.zookeeper.curator_api;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Description: 
 * date: 2021/5/29 16:57
 * @author YeJiang
 * @version
 */
public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .connectString("47.110.129.240:2181")
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curator.start();
        System.out.println("status:"+curator.getState());
        System.out.println(curator.checkExists().forPath("/root"));
        System.out.println(curator.checkExists().forPath("/root1"));
    }
}

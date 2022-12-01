package com.muyer.zookeeper.apache_api;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Description: 
 * date: 2021/5/29 19:05
 * @author YeJiang
 * @version
 */
public class ConnectDemo {

    public static void main(String[] args) throws InterruptedException, IOException {
        ZooKeeper zk = new ZooKeeper("47.110.129.240:2181", 4000, null);

        System.out.println(zk.getState());

        Thread.sleep(5000);

        System.out.println(zk.getState());

        zk.close();
    }
}

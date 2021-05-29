package com.muyer.zookeeper.apache_api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Description: 
 * date: 2021/5/29 19:14
 * @author YeJiang
 * @version
 */
public class ActDemo {

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {

        final CountDownLatch downLatch = new CountDownLatch(1);

        final ZooKeeper zk = new ZooKeeper("47.110.129.240:2181", 4000, event -> {
            System.out.println("默认事件:" + event.getType());
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                downLatch.countDown();
            }
        });

        downLatch.await();

        zk.create("/zk-yejiang", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        Thread.sleep(1000);

        Stat stat = new Stat();

        printData(zk, stat);

        zk.setData("/zk-yejiang", "2".getBytes(), stat.getVersion());

        printData(zk, stat);

        zk.delete("/zk-yejiang", stat.getVersion());

        zk.close();
    }

    private static void printData(ZooKeeper zk, Stat stat) throws KeeperException, InterruptedException {

        byte[] data = zk.getData("/zk-yejiang", null, stat);

        System.out.println(new String(data));
    }
}

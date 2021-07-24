package com.muyer.threadpool;

/**
 * Description: 
 * date: 2021/7/22 20:52
 * @author YeJiang
 * @version
 */
public interface Executor {
    void execute(Runnable task);
}

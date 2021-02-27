package com.muyer.forkjoin;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * Description: 
 * date: 2021/2/25 11:59
 * @author YeJiang
 * @version
 */
public class Task1 extends RecursiveTask<Integer> {

    private int[] args;

    public Task1(int[] args) {
        this.args = args;
    }

    @Override
    protected Integer compute() {
        if (args.length <= 2) {
            if (args.length == 1) {
                return args[0];
            }
            return args[0] > args[1] ? args[0] : args[1];
        } else {
            int mid = args.length / 2;
            Task1 left = new Task1(Arrays.copyOf(args, mid));
            Task1 right = new Task1(Arrays.copyOfRange(args, mid, args.length));
            invokeAll(left, right);
            Integer leftVale = left.join();
            Integer rightValue = right.join();
            return leftVale > rightValue ? leftVale : rightValue;
        }
    }
}

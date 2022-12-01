package com.muyer.leetcode.model;

/**
 * Description: 
 * date: 2021/3/19 10:04
 * @author YeJiang
 * @version
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

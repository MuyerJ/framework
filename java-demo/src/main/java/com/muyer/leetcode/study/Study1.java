package com.muyer.leetcode.study;

/**
 * Description: 
 * date: 2021/3/17 13:38
 * @author YeJiang
 * @version
 */
public class Study1 {

    public static void main(String[] args) {

    }

    /**
     * 荷兰国旗问题：partition
     * intput : arr 和 num
     * desc: 小于num放左边、大于num放右边、等于的放中间
     *
     * 空间复杂度 O(1);时间复杂度 O(N)
     *
     */
    public int[] partition(int[] arr, int left, int right, int num) {
        int less = left - 1;
        int more = right + 1;

        while (less < more) {
            if (arr[left] < num) {
                swap(arr, ++less, left++);
            } else if (arr[right] > num) {
                swap(arr, --more, left);
            } else {
                left++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    private void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}

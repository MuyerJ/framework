package com.muyer.mapplanning;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 
 * date: 2022/4/4 18:24
 * @author YeJiang
 * @version
 */
public class SubSet {

    private static void subset(ArrayList<Integer> inner, List<Integer> numList, ArrayList<ArrayList<Integer>> result, int start) {
        for (int i = start; i < numList.size(); i++) {
            inner.add(numList.get(i));
            result.add(new ArrayList<>(inner));
            subset(inner, numList, result, i + 1);
            inner.remove(inner.size() - 1);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            System.out.println(getPathListV2(i));
            System.out.println(getPathList( i));
        }

    }

    public static List<String> getPathListV2(int size){
        int start = 1;
        int end = size-2;
        List<String> list = Lists.newArrayList();
        for (int i = start; i <= end; i++) {
            list.add(i+"");
            for (int j = i + 1; j <= end; j++) {
                list.add(i + "," + j);
                for (int k = j + 1; k <= end; k++) {
                    list.add(i + "," + j + "," + k);
                }

            }
        }
        return list;
    }

    /**
     * 不含头不含尾
     *  例：size=5  ,只在 1-4中找subSet
     * @param size
     */
    public static List<String> getPathList(int size) {
        List<Integer> numList = Lists.newArrayList();
        for (int i = 1; i < size - 1; i++) {
            numList.add(i);
        }
        ArrayList<ArrayList<Integer>> result = Lists.newArrayListWithExpectedSize(getListSize(size));
        ArrayList<Integer> inner = new ArrayList<>();
        result.add(inner);
        subset(inner, numList, result, 0);
        List<String> res = Lists.newArrayList();
        for (ArrayList<Integer> each : result) {
            if (CollectionUtils.isEmpty(each) ||
                    !CollectionUtils.isEmpty(each) && each.size() > 3) {
                continue;
            }
            String join = StringUtils.join(each, ",");
            if (StringUtils.isEmpty(join)) {
                continue;
            }
            //System.out.println(join);
            res.add(join);
        }
        return res;
    }

    private static int getListSize(int size) {
        if (size <= 10) {
            return 381;
        }
        if (size <= 15) {
            return 3472;
        }
        if (size <= 18) {
            return 9401;
        }
        if (size <= 20) {
            return 16663;
        }
        if (size <= 22) {
            return 27895;
        }
        return 50000;
    }


}

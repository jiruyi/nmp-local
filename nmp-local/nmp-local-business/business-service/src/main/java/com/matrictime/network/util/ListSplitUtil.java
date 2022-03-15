package com.matrictime.network.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 大list拆分为多个小list处理
 * @param <T>
 */
public class ListSplitUtil<T extends Object> {
    /**
     *
     * @param list 要拆分的list
     * @param size 每次拆分的大小
     * @param <T>
     * @return
     */
    public static<T> List<List<T>> split(List<T> list, int size){
        if(list==null || list.size()==0){
            return null;
        }
        // 获得数据总量
        int count = list.size();
        // 计算出要分成几个批次
        int pageCount = (count / size) + (count % size == 0 ? 0 : 1);
        List<List<T>> temp = new ArrayList<>(pageCount);
        for (int i = 0, from = 0,to = 0; i < pageCount; i++) {
            from = i*size;
            to = from+size;
            // 如果超过总数量，则取到最后一个数的位置
            to = to>count?count:to;
            // 对list 进行拆分
            List<T> list1 = list.subList(from, to);
            // 将拆分后的list放入大List返回
            temp.add(list1);
            // 也可以改造本方法，直接在此处做操作
        }
        return temp;
    }

    // 测试方法
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            list.add(i);
        }
        List<List<Integer>> split = split(list, 10);
        for (int i = 0,length = split.size(); i < length; i++) {
            System.out.println(split.get(i));
        }
    }
}

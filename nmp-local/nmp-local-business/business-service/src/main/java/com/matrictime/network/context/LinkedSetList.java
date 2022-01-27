package com.matrictime.network.context;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/19 0019 14:55
 * @desc
 */
public class LinkedSetList<T>  extends LinkedList<T> {

    @Override
    public boolean add(T o) {
        if (size() == 0) {
            return super.add(o);
        } else {
            int count = 0;
            for (T t : this) {
                if (t.equals(o)) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                return super.add(o);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if(size() == 0){
            return super.addAll(c);
        }
        if(CollectionUtils.isEmpty(c)){
            return false;
        }
        for(T t: c){
            if (!this.contains(t)) {
                this.add(t);
            }
        }
        return true;
    }
}

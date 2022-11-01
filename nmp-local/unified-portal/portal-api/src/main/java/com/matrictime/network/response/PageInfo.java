package com.matrictime.network.response;


import java.util.List;

/**
  * @title
  * @param 
  * @return 
  * @description 
  * @author jiruyi
  * @create 2021/9/8 0008 15:33
  */
public class PageInfo<T> {

    /**
     * 每页查多少行
     */
    private int count;
    /**
     * 页码
     */
    private int pages;


    private List<T> list;

    public PageInfo() {
    }

    public PageInfo(int count, int pages, List<T> list) {
        this.count = count;
        this.pages = pages;
        this.list = list;
    }
    public PageInfo(List<T> list) {
        this.list = list;
    }
    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount(int totalElements, int pageSize) {
        this.count = totalElements;
        if (pageSize != 0) {
            this.pages = totalElements % pageSize > 0 ? totalElements / pageSize + 1 : totalElements / pageSize;
        }

    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PageResult{count=" + this.count + ", pages=" + this.pages + '}';
    }

}
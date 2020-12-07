package com.bjpowernode.crm.vo;

import java.util.List;

/**
 * 动力节点舜顺
 * 2019/12/12 0012
 */
public class PaginationVo<T> {

    private List<T> dataList;
    private int total;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

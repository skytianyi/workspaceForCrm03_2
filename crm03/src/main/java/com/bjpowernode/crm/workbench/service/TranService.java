package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/24 0024
 */
public interface TranService {

    void saveTran(String customerName, Tran t);

    Tran getTranByIdForOwner(String tranId);

    void updateTran(Tran t);

    Map<String,Object> getChart();

}

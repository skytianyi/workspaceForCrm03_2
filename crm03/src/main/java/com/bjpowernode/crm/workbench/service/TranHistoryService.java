package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

/**
 * 动力节点舜顺
 * 2019/12/24 0024
 */
public interface TranHistoryService {
    List<TranHistory> getHistoryListByTranId(String tranId);
}

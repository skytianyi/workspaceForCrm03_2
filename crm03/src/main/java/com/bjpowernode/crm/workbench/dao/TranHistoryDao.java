package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    void saveTranHistory(TranHistory tranHistory);

    List<TranHistory> getHistoryListByTranId(String tranId);
}

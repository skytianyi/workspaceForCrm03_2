package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    void saveTran(Tran t);

    Tran getTranByIdForOwner(String tranId);

    void updateTran(Tran t);



    List<String> getTranStage();

    List<Map<String, Object>> getChart();
}

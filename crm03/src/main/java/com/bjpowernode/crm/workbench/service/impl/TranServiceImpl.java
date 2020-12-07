package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/24 0024
 */
@Service
public class TranServiceImpl implements TranService {

    @Autowired
    private TranDao tranDao;

    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    public void saveTran(String customerName, Tran t) {
        Customer cust = customerDao.getCustomerNameByName(customerName);
        if(cust==null){
            cust=new Customer();
            cust.setId(UUIDUtil.getUUID());
            cust.setName(t.getName());
            cust.setDescription(t.getDescription());
            cust.setOwner(t.getOwner());
            cust.setContactSummary(t.getContactSummary());
            cust.setNextContactTime(t.getNextContactTime());
            cust.setCreateTime(DateTimeUtil.getSysTime());
            cust.setCreateBy(t.getCreateBy());
            customerDao.saveCustomer(cust);
        }

        String id = cust.getId();
        t.setCustomerId(id);

        tranDao.saveTran(t);

        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(t.getCreateBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setStage(t.getStage());
        th.setTranId(t.getId());

        tranHistoryDao.saveTranHistory(th);

    }

    @Override
    public Tran getTranByIdForOwner(String tranId) {
        Tran t=tranDao.getTranByIdForOwner(tranId);
        return t;
    }

    @Override
    public void updateTran(Tran t) {
        tranDao.updateTran(t);

        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setMoney(t.getMoney());
        th.setStage(t.getStage());
        th.setPossibility(t.getPossibility());
        th.setTranId(t.getId());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getEditBy());

        tranHistoryDao.saveTranHistory(th);
    }

    @Override
    public Map<String, Object> getChart() {
        List<String> nameList=tranDao.getTranStage();
        List<Map<String,Object>> dataList=tranDao.getChart();
        Map<String,Object> map=new HashMap<>();
        map.put("nameList",nameList);
        map.put("dataList",dataList);
        return map;
    }
}

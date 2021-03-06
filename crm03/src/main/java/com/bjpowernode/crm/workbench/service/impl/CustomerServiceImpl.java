package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动力节点舜顺
 * 2019/12/22 0022
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerNameListByName(String name) {
        List<String> sList=customerDao.getCustomerNameListByName(name);
        return sList;
    }
}

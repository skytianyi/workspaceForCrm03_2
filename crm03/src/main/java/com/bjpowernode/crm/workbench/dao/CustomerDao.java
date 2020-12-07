package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

    public interface CustomerDao {

    List<String> getCustomerNameListByName(String name);

    Customer getCustomerNameByName(String company);

    void saveCustomer(Customer cust);
}

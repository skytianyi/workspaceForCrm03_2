package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

/**
 * 动力节点舜顺
 * 2019/12/22 0022
 */
public interface ContactsService {


    List<Contacts> getContactsListByFullname(String fullname);
}

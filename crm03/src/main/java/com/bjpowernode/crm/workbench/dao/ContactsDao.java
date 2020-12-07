package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {


    List<Contacts> getContactsListByFullname(String fullname);

    void saveContacts(Contacts con);
}

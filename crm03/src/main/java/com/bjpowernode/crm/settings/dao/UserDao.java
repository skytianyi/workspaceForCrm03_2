package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/3 0003
 */
public interface UserDao {
    User login(Map<String, String> map);

    List<User> getUserList();

}

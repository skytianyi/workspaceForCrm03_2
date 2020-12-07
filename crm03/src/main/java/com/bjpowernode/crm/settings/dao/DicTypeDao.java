package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

/**
 * 动力节点舜顺
 * 2019/12/6 0006
 */
public interface DicTypeDao {

    List<DicType> selectDicType();

    int checkedCode(String code);

    int insertType(DicType dt);

    DicType selectByCode(String code);

    void updateType(DicType dt);

    void deleteType(String[] codes);


}

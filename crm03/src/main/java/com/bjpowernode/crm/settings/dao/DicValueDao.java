package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * 动力节点舜顺
 * 2019/12/6 0006
 */
public interface DicValueDao {

    List<DicValue> selectDicValue();

    int checkValueTypeCode(DicValue dicValue);

    void saveDicValue(DicValue dicValue);

    DicValue getDicValueByValTypeCode(DicValue dicValue);


    void updateDicValue(DicValue dicValue);

    void deleteValueByCode(String[] codes);


    void deleteValue(String[] ids);


    List<DicValue> getDicValueByCode(String code);
}

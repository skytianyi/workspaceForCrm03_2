package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.exception.AjaxRequestException;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/6 0006
 */
public interface DictionaryService {

    List<DicType> findDicType();

    boolean checkedCode(String code);

    int addType(DicType dt);

    DicType selectByCode(String code);

    void updateType(DicType dt);


    void deleteType(String[] codes);


    List<DicValue> selectDicValue();

    boolean checkValueTypeCode(DicValue dicValue);

    void saveDicValue(DicValue dicValue);

    DicValue getDicValueByValTypeCode(DicValue dicValue);


    void updateDicValue(DicValue dicValue) throws AjaxRequestException;

    void deleteValue(String[] ids);

    Map<String, List<DicValue>> getAll();

}

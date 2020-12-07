package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.exception.AjaxRequestException;
import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/6 0006
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DicTypeDao dicTypeDao;

    @Autowired
    private DicValueDao dicValueDao;


    @Override
    public List<DicType> findDicType() {

        return dicTypeDao.selectDicType();
    }

    @Override
    public boolean checkedCode(String code) {
        int i = dicTypeDao.checkedCode(code);

        if(i>0){

            return false;
        }else{
            return true;
        }

    }

    @Override
    public int addType(DicType dt) {
        return dicTypeDao.insertType(dt);
    }

    @Override
    public DicType selectByCode(String code) {
        DicType dicType = dicTypeDao.selectByCode(code);
        return dicType;
    }

    @Override
    public void updateType(DicType dt) {
        dicTypeDao.updateType(dt);
    }


    @Override
    public void deleteType(String[] codes) {

        dicValueDao.deleteValueByCode(codes);
        dicTypeDao.deleteType(codes);
    }

    @Override
    public List<DicValue> selectDicValue() {
        return dicValueDao.selectDicValue();
    }

    @Override
    public boolean checkValueTypeCode(DicValue dicValue) {
        int i = dicValueDao.checkValueTypeCode(dicValue);
        if(i>0){
            return false;
        }else{
            return true;
        }

    }

    @Override
    public void saveDicValue(DicValue dicValue) {
        dicValueDao.saveDicValue(dicValue);
    }

    @Override
    public DicValue getDicValueByValTypeCode(DicValue dicValue) {
       return   dicValueDao.getDicValueByValTypeCode(dicValue);
    }


    @Override
    public void updateDicValue(DicValue dicValue) throws AjaxRequestException {
        dicValueDao.updateDicValue(dicValue);

        int i = dicValueDao.checkValueTypeCode(dicValue);
        if(i>1){
            throw new AjaxRequestException("字典值重复了");
        }


    }

    @Override
    public void deleteValue(String[] ids) {


        dicValueDao.deleteValue(ids);
    }

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map=new HashMap<>();
        List<DicType> dicTypes = dicTypeDao.selectDicType();
        for (DicType dt:dicTypes) {
            String code = dt.getCode();
            List<DicValue> dvList = dicValueDao.getDicValueByCode(code);
            map.put(code+"List", dvList);
        }
        return map;
    }


}

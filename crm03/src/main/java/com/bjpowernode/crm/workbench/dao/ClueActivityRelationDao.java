package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    void deleteRelation(String carId);

    void saveRelation(List<ClueActivityRelation> carList);


    List<ClueActivityRelation> getClueActivityRelationByClueId(String clueId);

    void deleteRelationByClueId(String clueId);
}

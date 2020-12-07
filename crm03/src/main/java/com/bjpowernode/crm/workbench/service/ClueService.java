package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

/**
 * 动力节点老崔
 * 2019/12/16
 */
public interface ClueService {

    void saveClue(Clue clue);

    Clue getClueByIdForOwner(String id);

    void deleteRelation(String carId);

    void saveRelation(String clueId, String[] activityIds);

    void convert(String flag, Tran t, String createBy, String clueId);
}

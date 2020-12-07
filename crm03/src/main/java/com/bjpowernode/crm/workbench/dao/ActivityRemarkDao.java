package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * 动力节点舜顺
 * 2019/12/12 0012
 */
public interface ActivityRemarkDao {

    void deleteAct_remarkByActId(String[] ids);

    List<ActivityRemark> getRemarkListByActivityId(String activityId);

    void deleteRemark(String id);

    void saveRemark(ActivityRemark ar);

    void updateRemark(ActivityRemark ar);
}

package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/10 0010
 */
public interface ActivityDao {

    void saveActivity(Activity activity);

    List<Activity> getActivityByCondition(Map<String,Object> paramMap);

    int getTotalByCondition(Map<String,Object> paramMap);

    void deleteActivity(String[] ids);

    int importActivityList(List<Activity> aList);


    Activity getActivityByIdForOwner(String id);

    void updateActivity(Activity activity);

    List<Activity> getActivityListByCLueId(String clueId);

    List<Activity> getActivityListByNameNotClueId(Map<String, Object> paramMap);


    List<Activity> getActivityByName(String aname);
}

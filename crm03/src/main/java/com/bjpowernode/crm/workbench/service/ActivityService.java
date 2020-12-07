package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/10 0010
 */
public interface ActivityService {

    void saveActivity(Activity activity);

    Map<String,Object> pageList(Map<String,Object> paramMap);

    void deleteActivity(String[] ids);

    int importActivityList(List<Activity> aList);

    Map<String, Object> toActivityListAndUserList(String id);

    void updateActivity(Activity activity);

    Activity getActivityByIdForOwner(String id);

    List<ActivityRemark> getRemarkListByActivityId(String activityId);

    void deleteRemark(String id);

    void saveRemark(ActivityRemark ar);

    void updateRemark(ActivityRemark ar);

    List<Activity> getActivityListByCLueId(String clueId);

    List<Activity> getActivityListByNameNotClueId(Map<String, Object> paramMap);

    List<Activity> getActivityByName(String aname);
}

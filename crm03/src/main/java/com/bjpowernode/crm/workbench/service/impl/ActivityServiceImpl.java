package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/10 0010
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityRemarkDao activityRemarkDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void saveActivity(Activity activity) {

            activityDao.saveActivity(activity);
    }

    @Override
    public Map<String, Object> pageList(Map<String, Object> paramMap) {
        List<Activity> dataList = activityDao.getActivityByCondition(paramMap);
        int total = activityDao.getTotalByCondition(paramMap);
        Map<String, Object> map=new HashMap<>();
        map.put("dataList", dataList);
        map.put("total", total);

        return map;
    }

    @Override
    public void deleteActivity(String[] ids) {
        activityRemarkDao.deleteAct_remarkByActId(ids);
        activityDao.deleteActivity(ids);
    }

    @Override
    public int importActivityList(List<Activity> aList) {
        int count=activityDao.importActivityList(aList);
        return count;
    }

    @Override
    public Map<String, Object> toActivityListAndUserList(String id) {
        Activity a=activityDao.getActivityByIdForOwner(id);
        List<User> uList = userDao.getUserList();
        Map<String,Object> map=new HashMap<>();
        map.put("a", a);
        map.put("uList", uList);
        return map;
    }

    @Override
    public void updateActivity(Activity activity) {
        activityDao.updateActivity(activity);
    }

    @Override
    public Activity getActivityByIdForOwner(String id) {
        Activity a=activityDao.getActivityByIdForOwner(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByActivityId(String activityId) {
        List<ActivityRemark> arList=activityRemarkDao.getRemarkListByActivityId(activityId);
        return arList;
    }

    @Override
    public void deleteRemark(String id) {
        activityRemarkDao.deleteRemark(id);
    }

    @Override
    public void saveRemark(ActivityRemark ar) {
        activityRemarkDao.saveRemark(ar);
    }

    @Override
    public void updateRemark(ActivityRemark ar) {
        activityRemarkDao.updateRemark(ar);
    }

    @Override
    public List<Activity> getActivityListByCLueId(String clueId) {
        List<Activity> aList=activityDao.getActivityListByCLueId(clueId);
        return aList;

    }

    @Override
    public List<Activity> getActivityListByNameNotClueId(Map<String, Object> paramMap) {
        List<Activity> aList=activityDao.getActivityListByNameNotClueId(paramMap);
        return aList;
    }

    @Override
    public List<Activity> getActivityByName(String aname) {
        List<Activity> aList=activityDao.getActivityByName(aname);
        return aList;
    }
}

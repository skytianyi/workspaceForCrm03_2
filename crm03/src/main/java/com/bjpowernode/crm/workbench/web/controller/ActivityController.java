package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.exception.AjaxRequestException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.HandleFlag;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 动力节点舜顺
 * 2019/12/10 0010
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService actService;

    @RequestMapping("/toIndex.do")
    public String toIndex(){

        return "/workbench/activity/index";
    }

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){

        List<User> userList = userService.getUserList();

        return userList;
    }

    @RequestMapping("/saveActivity.do")
    @ResponseBody
    public Map<String,Object> saveActivity(Activity activity, HttpSession session){

        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        User user = (User) session.getAttribute("user");
        activity.setCreateBy(user.getName());

        actService.saveActivity(activity);

        return HandleFlag.successTrue();
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public Map<String,Object> pageList(@RequestParam Map<String,Object> paramMap){
        Integer pageNo = Integer.valueOf((String) paramMap.get("pageNoStr"));
        Integer pageSize =Integer.valueOf((String) paramMap.get("pageSizeStr"));
        int skipCount=(pageNo-1)*pageSize;

        paramMap.put("skipCount",skipCount);
        paramMap.put("pageSize",pageSize);
        Map<String, Object> map = actService.pageList(paramMap);

        return map;
    }

    @RequestMapping("/deleteActivity.do")
    @ResponseBody
    public Map<String,Object> deleteActivity(String[] ids){

        actService.deleteActivity(ids);
        return HandleFlag.successTrue();
    }

    @RequestMapping("/toActivityListAndUserList.do")
    @ResponseBody
    public Map<String,Object> toActivityListAndUserList(String id){
        Map<String,Object> map=actService.toActivityListAndUserList(id);
        return map;
    }

    @RequestMapping("/updateActivity.do")
    @ResponseBody
    public Map<String,Object> updateActivity(Activity activity, HttpSession session){
        User user = (User) session.getAttribute("user");
        activity.setEditBy(user.getName());
        activity.setEditTime(DateTimeUtil.getSysTime());
        actService.updateActivity(activity);

        return HandleFlag.successTrue();
    }


    @RequestMapping("/importActivityList.do")
    @ResponseBody
    public Map<String,Object> importActivityList(@RequestParam("myFile")MultipartFile file, HttpServletRequest request) throws AjaxRequestException {

        try {
            String filename = DateTimeUtil.getSysTimeForUpload() + ".xls";
            String path = request.getServletContext().getRealPath("/tmpDir");
            file.transferTo(new File(path+"/"+filename));

            InputStream input=new FileInputStream(path+"/"+filename);
            HSSFWorkbook workbook=new HSSFWorkbook(input);
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Activity> aList=new ArrayList<>();
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                HSSFRow row = sheet.getRow(i);
                Activity a=new Activity();
                a.setId(UUIDUtil.getUUID());
                for(int j=1;j<row.getLastCellNum();j++){
                    HSSFCell cell = row.getCell(j);
                    if(j==1){
                        a.setOwner(cell.getStringCellValue());
                    }if(j==2){
                        a.setName(cell.getStringCellValue());
                    }if(j==3){
                        a.setStartDate(cell.getStringCellValue());
                    }if(j==4){
                        a.setEndDate(cell.getStringCellValue());
                    }if(j==5){
                        a.setCost(cell.getStringCellValue());
                    }if(j==6){
                        a.setDescription(cell.getStringCellValue());
                    }if(j==7){
                        a.setCreateTime(cell.getStringCellValue());
                    }if(j==8){
                        a.setCreateBy(cell.getStringCellValue());
                    }if(j==9){
                        a.setEditTime(cell.getStringCellValue());
                    }if(j==10){
                        a.setEditBy(cell.getStringCellValue());
                    }
                }
                aList.add(a);
            }
            int count=actService.importActivityList(aList);
            return HandleFlag.successObj("count", count);
        } catch (Exception e) {
            e.printStackTrace();

            throw new AjaxRequestException();
        }

    }

    @RequestMapping("/toActivityDetail.do")
    public ModelAndView toActivityDetail(String id){
        Activity a=actService.getActivityByIdForOwner(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("a", a);
        mv.setViewName("/workbench/activity/detail");
        return mv;
    }

    @RequestMapping("/getRemarkListByActivityId.do")
    @ResponseBody
    public  List<ActivityRemark> getRemarkListByActivityId(String activityId){
        List<ActivityRemark> arList=actService.getRemarkListByActivityId(activityId);
        return arList;
    }

    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    public Map<String,Object> deleteRemark(String id){
        actService.deleteRemark(id);
        return HandleFlag.successTrue();
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Map<String,Object> saveRemark(ActivityRemark ar,HttpSession session){

        ar.setEditFlag("0");
        ar.setId(UUIDUtil.getUUID());
        String createBy = ((User)session.getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        actService.saveRemark(ar);
        return HandleFlag.successObj("ar", ar);
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(ActivityRemark ar,HttpSession session){

        String editBy = ((User)session.getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        ar.setEditFlag("1");
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);

        actService.updateRemark(ar);
        return HandleFlag.successObj("ar", ar);
    }

}

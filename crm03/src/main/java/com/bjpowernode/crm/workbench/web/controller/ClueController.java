package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.HandleFlag;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 动力节点老崔
 * 2019/12/16
 */
@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClueService clueService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/toClueIndex.do")
    public String toClueIndex(){

        return "/workbench/clue/index";

    }

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        List<User> uList=userService.getUserList();
        return uList;
    }

    @RequestMapping("/saveClue.do")
    @ResponseBody
    public Map<String,Object> saveClue(Clue clue, HttpSession session){
        String createBy = ((User)session.getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(createTime);
        clue.setCreateBy(createBy);
        clueService.saveClue(clue);

        return HandleFlag.successTrue();
    }

    @RequestMapping("/toClueDetail.do")
    public ModelAndView toClueDetail(String id){
        Clue c=clueService.getClueByIdForOwner(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("c", c);
        mv.setViewName("/workbench/clue/detail");
        return mv;
    }

    @RequestMapping("/getActivityListByCLueId.do")
    @ResponseBody
    public List<Activity> getActivityListByCLueId(String clueId){
        List<Activity> aList=activityService.getActivityListByCLueId(clueId);
        return aList;
    }

    @RequestMapping("/unband.do")
    @ResponseBody
    public Map<String,Object> unband(String carId){

        clueService.deleteRelation(carId);
        return HandleFlag.successTrue();
    }

    @RequestMapping("/getActivityListByNameNotClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByNameNotClueId(@RequestParam Map<String,Object> paramMap){
        List<Activity> aList=activityService.getActivityListByNameNotClueId(paramMap);
        return aList;
    }

    @RequestMapping("/band.do")
    @ResponseBody
    public Map<String,Object> band(String clueId,String[] activityIds){

        clueService.saveRelation(clueId,activityIds);
        return HandleFlag.successTrue();
    }

    @RequestMapping("/toConvert.do")
    public ModelAndView toConvert(Clue c){
        ModelAndView mv=new ModelAndView();
        mv.addObject("c", c);
        mv.setViewName("/workbench/clue/convert");
        return mv;
    }

    @RequestMapping("/getActivityByName.do")
    @ResponseBody
    public List<Activity> getActivityByName(String aname){
        List<Activity>  aList=activityService.getActivityByName(aname);
        return aList;
    }

    @RequestMapping("/convert.do")
    public String convert(String flag, Tran t,HttpSession session,String clueId){
        String createBy = ((User)session.getAttribute("user")).getName();

        clueService.convert(flag,t,createBy,clueId);
        return "/workbench/clue/index";
    }

}























































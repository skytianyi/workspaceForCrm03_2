package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.HandleFlag;
import com.bjpowernode.crm.utils.SetCreateUtil;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.ContactsService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/22 0022
 */
@Controller
@RequestMapping("/workbench/transaction")
public class TranSactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private TranService tranService;

    @Autowired
    private TranHistoryService tranHistoryService;



    @RequestMapping("/toTranIndex.do")
    public String toTranIndex(){

        return "/workbench/transaction/index";
    }

    @RequestMapping("/toSave.do")
    @ResponseBody
    public ModelAndView toSave(){
        List<User> uList = userService.getUserList();
        ModelAndView mv=new ModelAndView();
        mv.addObject("uList", uList);
        mv.setViewName("/workbench/transaction/save");
        return mv;
    }

    @RequestMapping("/getCustomerNameListByName.do")
    @ResponseBody
    public List<String> getCustomerNameListByName(String name){
        List<String> sList=customerService.getCustomerNameListByName(name);
        return sList;
    }

    @RequestMapping("/getContactsListByFullname.do")
    @ResponseBody
    public List<Contacts> getContactsListByFullname(String fullname){
        List<Contacts> cList=contactsService.getContactsListByFullname(fullname);
        return cList;
    }

    @RequestMapping("/getPossibilityByStage.do")
    @ResponseBody
    public Map<String,String> getPossibilityByStage(String stage, HttpServletRequest request){
        ServletContext application = request.getServletContext();
        Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");
        String possibility=pMap.get(stage);
        Map<String,String> map=new HashMap<>();
        map.put("possibility", possibility);
        return map;
    }

    @RequestMapping("/saveTran.do")
    public String saveTran(String customerName, Tran t, HttpSession session) throws Exception {
        SetCreateUtil.setCreNaByAndId(session, t);
        tranService.saveTran(customerName,t);

        return "redirect:/workbench/transaction/toTranIndex.do";
    }

    @RequestMapping("/toDetail.do")
    public ModelAndView toDetail(String tranId,HttpServletRequest request){
        Tran t=tranService.getTranByIdForOwner(tranId);
        ServletContext application = request.getServletContext();
        Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");
        String possibility = pMap.get(t.getStage());
        t.setPossibility(possibility);
        ModelAndView mv=new ModelAndView();
        mv.addObject("t", t);
        mv.setViewName("/workbench/transaction/detail");

        return mv;
    }

    @RequestMapping("/getHistoryListByTranId.do")
    @ResponseBody
    public List<TranHistory> getHistoryListByTranId(String tranId,HttpServletRequest request){
        List<TranHistory> thList=tranHistoryService.getHistoryListByTranId(tranId);
        ServletContext application = request.getServletContext();
        Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");

        for(TranHistory th:thList){
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibility(possibility);
        }
        return thList;
    }

    @RequestMapping("/chageStage.do")
    @ResponseBody
    public Map<String,Object> chageStage(Tran t,HttpServletRequest request){

        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        t.setEditBy(editBy);
        t.setEditTime(editTime);
        ServletContext application = request.getServletContext();
        Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");
        String possibillity = pMap.get(t.getStage());
        t.setPossibility(possibillity);
        tranService.updateTran(t);

        return HandleFlag.successObj("t", t);
    }


}

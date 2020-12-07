package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.exception.AjaxRequestException;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DictionaryService;
import com.bjpowernode.crm.utils.HandleFlag;
import com.bjpowernode.crm.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 动力节点舜顺
 * 2019/12/6 0006
 */

@Controller
@RequestMapping("/settings/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dicyService;

    @RequestMapping("/toIndex.do")
    public String toIndex(){

        return "/settings/dictionary/index";
    }

    @RequestMapping("/toTypeIndex.do")
    public ModelAndView toTypeIndex(){
        List<DicType> dtList = dicyService.findDicType();
        ModelAndView mv=new ModelAndView();
        mv.addObject("dtList", dtList);
        mv.setViewName("/settings/dictionary/type/index");

        return mv;
    }

    @RequestMapping("/toTypeSave.do")
    public String toTypeSave(){

        return "/settings/dictionary/type/save";
    }

    @RequestMapping("/type/checkedCode.do")
    @ResponseBody
    public Map<String,Object> checkedCode(String code){
        boolean flag = dicyService.checkedCode(code);
        Map<String,Object> map=new HashMap<>();
        map.put("success", flag);
        return map;
    }

    @RequestMapping("/type/Save.do")
    public String save(DicType dt){
        int i = dicyService.addType(dt);
        if(i>0){
            return "redirect:/settings/dictionary/toIndex.do";
        }else{
            return "redirect:/fail";
        }
    }

    @RequestMapping("/toTypeUpdate.do")
    public ModelAndView toTypeUpdate(String code){
        DicType dicType = dicyService.selectByCode(code);
        ModelAndView mv=new ModelAndView();
        mv.addObject("dicType", dicType);
        mv.setViewName("/settings/dictionary/type/edit");
        return mv;
    }

    @RequestMapping("/updateType.do")
    public String updateType(DicType dt){
        dicyService.updateType(dt);
        return "redirect:/settings/dictionary/toTypeIndex.do";
    }

    @RequestMapping("/deleteType.do")
    public String deleteType(String[] codes){

        dicyService.deleteType(codes);
        return "redirect:/settings/dictionary/toTypeIndex.do";
    }

    @RequestMapping("/toValueIndex.do")
    public ModelAndView toValueIndex(){
        List<DicValue> dicValues = dicyService.selectDicValue();
        ModelAndView mv=new ModelAndView();
        mv.addObject("dicValues", dicValues);
        mv.setViewName("/settings/dictionary/value/index");
        return mv;
    }

    @RequestMapping("/value/toSaveValue.do")
    public ModelAndView toSaveValue(){
        List<DicType> dicType = dicyService.findDicType();
        ModelAndView mv=new ModelAndView();
        mv.addObject("dicType", dicType);
        mv.setViewName("/settings/dictionary/value/save");

        return mv;

    }

    @RequestMapping("/value/checkValueTypeCode.do")
    @ResponseBody
    public Map<String,Object> checkValueTypeCode(DicValue dicValue){

        boolean flag = dicyService.checkValueTypeCode(dicValue);
        Map<String,Object> map=new HashMap<>();
        map.put("success", flag);
        return map;
    }

    @RequestMapping("/value/saveValue.do")
    public String saveValue(DicValue dicValue){
        dicValue.setId(UUIDUtil.getUUID());
        dicyService.saveDicValue(dicValue);
        return "redirect:/settings/dictionary/toValueIndex.do";
    }

    @RequestMapping("/value/toUpdateValue.do")
    public ModelAndView toUpdateValue(DicValue dicValue){

        ModelAndView mv=new ModelAndView();

        String code = dicValue.getTypeCode();
        DicType dicType = dicyService.selectByCode(code);
        String name = dicType.getName();

        DicValue dvData = dicyService.getDicValueByValTypeCode(dicValue);
        mv.addObject("name", name);
        mv.addObject("dvData", dvData);
        mv.setViewName("/settings/dictionary/value/edit");
        return  mv;
    }



    @RequestMapping("/value/updateValue.do")
    @ResponseBody
    public Map<String,Object> updateValue(DicValue dicValue) throws AjaxRequestException {

        dicyService.updateDicValue(dicValue);

        return HandleFlag.successTrue();

    }

    @RequestMapping("/value/deleteValue.do")
    public String deleteValue(String[] ids){
        dicyService.deleteValue(ids);
        return "redirect:/settings/dictionary/toValueIndex.do";
    }



}

package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 动力节点舜顺
 * 2019/12/25 0025
 */
@Controller
public class ChartController {

    @Autowired
    private TranService tranService;

    @RequestMapping("/workbench/chart/transaction/toIndex.do")
    public String toIndex(){

        return "/workbench/chart/transaction/index";
    }

    @RequestMapping("/workbench/chart/transaction/getChart.do")
    @ResponseBody
    public Map<String,Object> getChart(){
        Map<String,Object> map=tranService.getChart();
        return map;
    }


}

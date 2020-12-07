package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动力节点舜顺
 * 2019/12/5 0005
 */


@Controller
public class WorkBenchController {

    @RequestMapping("/workbench/toIndex.do")
    public String toIndex(){
        return "/workbench/index";
    }

    @RequestMapping("/workbench/toMainIndex.do")
    public String toMainIndex(){

        return "/workbench/main/index";
    }




}

package com.bjpowernode.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动力节点舜顺
 * 2019/12/6 0006
 */
@Controller
public class SettingsController {


    @RequestMapping("/settings/toIndex.do")
    public String toIndex(){
        return "/settings/index";
    }
}

package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DictionaryService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * 动力节点舜顺
 * 2019/12/17 0017
 */


public class SysInitListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext application = sce.getServletContext();
        DictionaryService dicService = WebApplicationContextUtils.getWebApplicationContext(application).getBean(DictionaryService.class);
        Map<String, List<DicValue>> map = dicService.getAll();
        Set<String> set = map.keySet();
        for (String key:set) {
            application.setAttribute(key, map.get(key));
        }

        System.out.println("服务器缓存存储阶段和可能性关系");

        ResourceBundle rb = ResourceBundle.getBundle("properties/Stage2Possibility");
        Map<String,String> pMap=new HashMap<>();
        Enumeration<String> e = rb.getKeys();
        while (e.hasMoreElements()){
            String key = e.nextElement();
            String value = rb.getString(key);
            System.out.println("key:"+key);
            System.out.println("value:"+value);
            pMap.put(key, value);
        }
        application.setAttribute("pMap", pMap);

    }
}

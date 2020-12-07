package com.bjpowernode.crm.utils;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Tran;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 动力节点舜顺
 * 2019/12/24 0024
 */
public class SetCreateUtil {

    public static void setCreNaByAndId(HttpSession session,Object o) throws Exception {

        String id=UUIDUtil.getUUID();
        String createBy = ((User)session.getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();

        String className="";
        if(o instanceof Tran){
            className="com.bjpowernode.crm.workbench.domain.Tran";
        }

        Class<?> c = Class.forName(className);
        Method method = c.getMethod("setId", String.class);
        method.invoke(o, id);

        Method method1 = c.getMethod("setCreateBy", String.class);
        method1.invoke(o, createBy);

        Method method2 = c.getMethod("setCreateTime", String.class);
        method2.invoke(o, createTime);

    }
}

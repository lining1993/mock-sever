package org.qianshengqian.common.utils;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author Peter
 * @Date 2017/5/31
 */
public class JavaValueUtils {

    /**
     * @Description 检测对象不为空的属性数量
     * @Author Peter
     * @Date ${DATE}
     */
    public static Integer checkValues(Object obj){
        if(obj==null){
            return 0;
        }
        Class cla=obj.getClass();
        Field[] fields=cla.getDeclaredFields();
        int count=0;
        for(Field field:fields){
            Object v=getValue(obj,field);
            if(v==null||StringUtils.isNullOrEmpty(String.valueOf(v))){
                continue;
            }else{
                count++;
            }
        }
        return count;
    }

    public static Object getValue(Object source,Field field){
        Object v=null;
        try {
            field.setAccessible(true);
            v=field.get(source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return v;
    }

}

package com.yuanshanbao.common.util;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public final class Duplicate {  
	  
    private Duplicate() {}  
  
    public static void checkDuplicate(Class cls) {  
        checkDuplicate(cls.getName().replace('.', '/') + ".class");  
    }  
  
    public static void checkDuplicate(String path) {  
        try {  
            // 在ClassPath搜文件  
            Enumeration urls = Thread.currentThread().getContextClassLoader().getResources(path);  
            Set files = new HashSet();  
            while (urls.hasMoreElements()) {  
                Object urlObj = urls.nextElement();  
                if (urlObj != null) {  
                	URL url = (URL)urlObj; 
                    String file = url.getFile();  
                    if (file != null && file.length() > 0) {  
                        files.add(file);  
                    }  
                }  
            }  
            // 如果有多个，就表示重复  
            if (files.size() > 1) {  
                LoggerUtil.alarmInfo("Duplicate class " + path + " in " + files.size() + " jar " + files);  
                System.out.println("Duplicate class " + path + " in " + files.size() + " jar " + files);
            }  
        } catch (Throwable e) { // 防御性容错  
            LoggerUtil.error(e.getMessage(), e);  
        }  
    }  
    
    public static void main(String[] args){
    	Duplicate.checkDuplicate("F:\\advertisement_info\\code\\advertisement_odds\\trunk\\web\\WEB-INF\\lib");
    }
  
}  
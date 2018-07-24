package com.chnghx.common.utils;


import javax.servlet.http.HttpServletRequest;

public class DomainUtil {
	
	/*** 
     * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP, 
     * @param request 
     * @return 
     */  
    public static String getRemoteHost(HttpServletRequest request) {
//        String fromSource = "X-Real-IP";
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
//            fromSource = "X-Forwarded-For";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
//            fromSource = "Proxy-Client-IP";
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
//            fromSource = "WL-Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
//            fromSource = "request.getRemoteAddr";
        }
//        System.out.println("App Client IP: "+ip+", fromSource: "+fromSource);
        return ip;
    }
    
    
    public static String getLocalHost(HttpServletRequest request) {
    	 String ip = request.getLocalAddr();
        return ip;
    }
	
   
}
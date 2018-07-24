package com.chnghx.core.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.chnghx.common.utils.LoggerUtils;
import com.chnghx.core.shiro.ShiroToken;
import com.chnghx.core.shiro.manager.TokenManager;

/**
 * 判断是否是登录的
 * @author guohaixiang
 *
 */
public class LoginFilter extends AccessControlFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
			ShiroToken token = TokenManager.getToken();
			HttpServletRequest req = (HttpServletRequest)request;
			
			if(token!=null){// && isEnabled()
//				String [] roles = (String[])mappedValue;
//				if(null != roles && roles.length >0){
//					boolean hasRole = false;
//					for(String role : roles){
//						if(role.equals(token.getLoginName())){
//							hasRole = true;
//							break;
//						}
//					}
//					return hasRole;
//				}else{
//					return false;
//				}
				return true;
			}else {
	            return false;
	        }
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		saveRequestAndRedirectToLogin(request, response);
		LoggerUtils.error(getClass(),"user info is empty");
		return false;
	}

}

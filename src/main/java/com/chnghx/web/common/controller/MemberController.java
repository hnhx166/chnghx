package com.chnghx.web.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.util.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.chnghx.common.utils.LoggerUtils;
import com.chnghx.common.utils.StringUtils;
import com.chnghx.core.shiro.ShiroToken;
import com.chnghx.core.shiro.manager.TokenManager;

@RestController
@RequestMapping("/open/member")
public class MemberController {
	static final Class<?> SELF = MemberController.class;
	/**
	 * 没有登录跳转到登录
	 * @return
	 */
	@RequestMapping("tologin")
	public ModelAndView tologin(){
		SavedRequest request = (SavedRequest) TokenManager.getSession().getAttribute("shiroSavedRequest");
		String url = request.getRequestUrl();
		String query = request.getQueryString();
		String method= request.getMethod();
		LoggerUtils.fmtDebug(SELF, "当前没有登录，访问的地址为：%s，请求类型为：%s，请求的参数为：%s", 
				url,request.getMethod(),query);
		//获取会员跳转登录url
		String memberLogin = "http://www.vinuxmembers.com/open/request/pc/login.vhtml?return_url=http://localhost/open/member/login.vhtml";
		LoggerUtils.fmtDebug(SELF, "获取会员跳转登录地址：%s", memberLogin);
		/**参数处理*/
		if(StringUtils.isNotBlank(url) || StringUtils.isNotBlank(query)){
			Map<String, String> map = StringUtils.getToMap(query);
			if(StringUtils.isNotBlank(url)){
				map.put("backUrl", url);
			}
			map.put("method", method);
			String args = StringUtils.mapToGet(map);
			args = StringUtils.getBASE64(args);
			memberLogin = String.format("%s?args=%s", memberLogin,args);
			LoggerUtils.fmtDebug(SELF, "获取会员跳转登录地址，加上参数：%s", memberLogin);
		}
		return new ModelAndView(new RedirectView(memberLogin));
	}
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request,String _access_token,String userInfo,String args) throws Exception{
		//弹出登录获取购物车地址
		String backUrl = request.getParameter("url");
		//会员SessionID  禁止使用此值
		if(StringUtils.isBlank(_access_token)){
			LoggerUtils.fmtError(SELF, "会员_access_token为null");
		}else{
			LoggerUtils.fmtError(SELF, "会员_access_token：%s" ,_access_token);
		}
		if(StringUtils.isNotBlank(userInfo)){
			//userInfo = URLDecoder.decode(userInfo,"utf-8");  去掉一次decode，避免%编码问题。
			ShiroToken token = new ShiroToken(userInfo);
			token.login();
			
			LoggerUtils.debug(getClass(), "登录成功，从会员返回成功。UserInfo：[" + userInfo + "]");
		}else{
			throw new Exception("登录成功，但从会员返回的UserInfo为null");
		}
		//当地址登录后返回的地址丢失了，那么就回跳到这个页面
		String indexUrl = "localhost:8086";
		
		String redirect = indexUrl;
		if(StringUtils.isNotBlank(args)){
			args = StringUtils.getStrByBASE64(args);
			Map<String, ? extends Object> map = StringUtils.getToMap(args);
			String getBackUrl = (String) map.get("backUrl");
			if(StringUtils.isNotBlank(backUrl)){
				redirect = backUrl;
			}
			if(StringUtils.isNotBlank(getBackUrl)){
				redirect = getBackUrl;
				map.remove("backUrl");
			}
			String method = (String) map.get("method");
			//TODO 待升级 未能解决method为POST的提交
			if(StringUtils.isNotBlank(method)){
				map.remove("method");
				if( method.equals("POST")){
					redirect = indexUrl;
				}
			}
			String query = StringUtils.mapToGet(map);
			if(StringUtils.isNotBlank(query)){
				redirect = 	String.format("%s?%s", redirect,query);
			}
		}
		//弹出登录获取回跳地址
		if(StringUtils.isNotBlank(backUrl)){
			return new ModelAndView("common/login_success","backUrl",backUrl);
		}
		
		return new ModelAndView(new RedirectView(redirect));
	}
}

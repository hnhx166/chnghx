package com.chnghx.core.shiro.manager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.chnghx.core.shiro.ShiroToken;

public class TokenManager {

	public static final String TOKEN_KEY = TokenManager.class.getCanonicalName() + "token_key";

	/**
	 * 获取当前登录的用户User对象
	 * 
	 * @return
	 */
	public static ShiroToken getToken() {
		return (ShiroToken) SecurityUtils.getSubject().getPrincipal();
	}

//	public static ShiroToken seToken(ShiroToken token) {
//		getSession().setAttribute(TOKEN_KEY, token);
//		return token;
//	}

	/**
	 * 获取当前用户的Session
	 * 
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 获取当前用户NAME
	 * 
	 * @return
	 */
	public static String getUserName() {
		return getToken().getUsername();
	}

	/**
	 * 获取当前用户ID
	 * 
	 * @return
	 */
	public static String getUserId() {
		return getToken() == null ? null : getToken().getUserId();
	}

	/**
	 * 把值放入到当前登录用户的Session里
	 * 
	 * @param key
	 * @param value
	 */
	public static void setVal2Session(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 从当前登录用户的Session里取值
	 * 
	 * @param key
	 * @return
	 */
	public static Object getVal2Session(Object key) {
		return getSession().getAttribute(key);
	}

	/**
	 * 获取当前用户的roles
	 * 
	 * @return
	 */
	// public static Set<String> getRoles(){
	// LinkedHashMap<String,Role> roles = (LinkedHashMap<String, Role>)
	// getSession().getAttribute(ROLES);
	// Set<String> roleKey = roles.keySet();
	// return roleKey ;
	// }
	// /**
	// * 获取当前用户的Auths
	// * @return
	// */
	// public static Set<String> getAuths(){
	// LinkedHashMap<String,Auth> auths = (LinkedHashMap<String, Auth>)
	// getSession().getAttribute(AUTHS);
	// Set<String> authKey = auths.keySet();
	// return authKey ;
	// }
	//

	public static boolean isLogin() {
		try {
			ShiroToken token = (ShiroToken) SecurityUtils.getSubject().getPrincipal();
			if (null != token) {
//				seToken(token);
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
	}
}

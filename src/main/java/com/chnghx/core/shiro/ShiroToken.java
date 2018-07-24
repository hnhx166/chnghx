package com.chnghx.core.shiro;

import java.io.Serializable;
import java.net.URLDecoder;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.alibaba.fastjson.JSONObject;
import com.chnghx.common.config.Config;
import com.chnghx.common.utils.LoggerUtils;
import com.chnghx.common.utils.StringUtils;

public class ShiroToken extends UsernamePasswordToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String EMPTY = "" ;
	public ShiroToken(String loginName, String userId) {
		super(loginName,userId);
		this.loginName = loginName;
		this.userId = userId;
	}
	
	private String careFirstOrderMedia;
	private String firstOrder;
	private String loginName;
	private String nickname;//昵称
	private String roleId;
	private String roleName;
	private String roleType;
	private String sellerType;
	private String userId;//用户ID
	private String userName;//登录帐号
	private String userType;
	private String vinuxId;
	private String lastLoginTime;
	
	private ShiroToken self ; //用户token存贮
	public ShiroToken(String userInfo) throws Exception {
		try {
			userInfo = URLDecoder.decode(userInfo,"utf-8");
			JSONObject u = JSONObject.parseObject(userInfo);
			
			LoggerUtils.debug(getClass(), "登录成功，从会员返回成功。UserInfo：[" + userInfo + "]");
			LoggerUtils.debug(getClass(), "开始构建ShiroToken对象");
			this.careFirstOrderMedia = u.getString("careFirstOrderMedia");
			this.firstOrder = u.getString("firstOrder");
			this.loginName = u.getString("loginName");
			this.nickname = u.getString("nickname");
			this.roleId = u.getString("roleId");
			this.roleName = u.getString("roleName");
			this.roleType = u.getString("roleType");
			this.sellerType = u.getString("sellerType");
			this.userId = u.getString("userId");
			this.userName = u.getString("userName");
			this.userType = u.getString("userType");
			this.vinuxId = u.getString("vinuxId");
			this.self = this ;
			LoggerUtils.debug(getClass(), "构建ShiroToken对象成功结束");
		} catch (Exception e) {
			LoggerUtils.error(getClass(), "构建ShiroToken对象失败！",e);
			throw e;
		}
	}
	
	public void login() throws Exception{
		try {
			ShiroToken token = new ShiroToken(loginName,userId);
			//从配置文件里获取要不要记住【登录状态】
			String rememberMe = Config.getProperty("shiro.rememberMe");
			Boolean isRememberMe = Boolean.FALSE;
			token.setSelf(this);
			if(StringUtils.isNotBlank(rememberMe)){
				isRememberMe = new Boolean(rememberMe);
			}
			token.setRememberMe(isRememberMe);
			SecurityUtils.getSubject().login(token);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ShiroToken() {
	}
	public String getCareFirstOrderMedia() {
		return careFirstOrderMedia;
	}
	public void setCareFirstOrderMedia(String careFirstOrderMedia) {
		this.careFirstOrderMedia = careFirstOrderMedia;
	}
	public String getFirstOrder() {
		return firstOrder;
	}
	public void setFirstOrder(String firstOrder) {
		this.firstOrder = firstOrder;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getVinuxId() {
		return vinuxId;
	}
	public void setVinuxId(String vinuxId) {
		this.vinuxId = vinuxId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ShiroToken getSelf() {
		return self;
	}

	public void setSelf(ShiroToken self) {
		this.self = self;
	}
	
	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}

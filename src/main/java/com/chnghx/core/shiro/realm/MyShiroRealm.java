package com.chnghx.core.shiro.realm;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.chnghx.core.shiro.ShiroToken;
import com.chnghx.core.shiro.manager.TokenManager;

public class MyShiroRealm extends AuthorizingRealm {
	
//	private final static String[] permissionAdmin = Config.getProperty("sso.permission.admin").split(",");
//	private final static List<String> permissionAdminList = new ArrayList<String>();
//	static{
//		Collections.addAll(permissionAdminList, permissionAdmin);
//	}
	
	public MyShiroRealm(){
		super();
	}

	/**
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		/**
//    	 * 在这可以给予当前用户的权限
//    	 */
//    	//用户名  
//    	ShiroToken token = (ShiroToken) principals.getPrimaryPrincipal();
//    	String username = token.getLoginName();
//         
//        /*这些代码应该是动态从数据库中取出的，此处写死*/  
//        if(StringUtils.isNotBlank(username) && permissionAdminList.contains(username)){
//            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//            info.addRole("admin");//添加一个角色，不是配置意义上的添加，而是证明该用户拥有admin角色  
//            info.addStringPermission("admin:manage");//添加管理权限  
//            return info;
//        }
    	return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		ShiroToken token = (ShiroToken) authcToken;
		/**
		 * 通过帐号获取用户对象
		 * 
		 * 
		 */
		if(null == token )//|| token.checkSelf()
			throw new AccountException("'token' is null or 'token' contains null value");
		
		//TODO 如果自己管理用户信息的，需要自主实现登录校验，如有问题咨询@周柏成
		
		String userId = new String(token.getUserId());
		token = token.getSelf();
//		TokenManager.seToken(token);
		
//		System.out.println(TokenManager.getToken());
		return new SimpleAuthenticationInfo(token,userId, getName());
	}

}

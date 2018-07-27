package com.chnghx.core.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisSessionDao extends AbstractSessionDAO {
	
	// Session超时时间，单位为毫秒
	private long expireTime = 120000;
	
	@Autowired
	private RedisTemplate redisTemplate;// Redis操作类，对这个使用不熟悉的，可以参考前面的博客
 
	public RedisSessionDao() {
		super();
	}
 
	public RedisSessionDao(long expireTime, RedisTemplate redisTemplate) {
		super();
		this.expireTime = expireTime;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		System.out.println("更新session");
		if(null == session || null == session.getId()) {
			return ;
		}
		session.setTimeout(expireTime);
		redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);;
	}

	@Override
	public void delete(Session session) {
		System.out.println("删除session");
		if(null == session || null == session.getId()) {
			return ;
		}
		redisTemplate.opsForValue().getOperations().delete(session.getId());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return redisTemplate.keys("*");
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(null == sessionId) {
			return null;
		}
		
		return (Session)redisTemplate.opsForValue().get(sessionId);
	}

	
	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}

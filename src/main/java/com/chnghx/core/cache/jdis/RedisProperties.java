package com.chnghx.core.cache.jdis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * redis配置以对象方式读入内容。
 * @author zzl
 *
 */
@Component("redisProperties")
public class RedisProperties {
	@Value("${spring.redis.database}")
	private Integer database;
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private Integer port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.pool.max-active}")
	private Integer maxActive;
	@Value("${spring.redis.pool.max-wait}")
	private Integer maxWait;
	@Value("${spring.redis.pool.max-idle}")
	private Integer MaxIdle;
	@Value("${spring.redis.pool.min-idle}")
	private Integer minIdle;
	@Value("${spring.redis.timeout}")
	private Long timeout;
	/**
	 * @return the database
	 */
	public Integer getDatabase() {
		return database;
	}
	/**
	 * @param database the database to set
	 */
	public void setDatabase(Integer database) {
		this.database = database;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the maxActive
	 */
	public Integer getMaxActive() {
		return maxActive;
	}
	/**
	 * @param maxActive the maxActive to set
	 */
	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}
	/**
	 * @return the maxWait
	 */
	public Integer getMaxWait() {
		return maxWait;
	}
	/**
	 * @param maxWait the maxWait to set
	 */
	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}
	/**
	 * @return the maxIdle
	 */
	public Integer getMaxIdle() {
		return MaxIdle;
	}
	/**
	 * @param maxIdle the maxIdle to set
	 */
	public void setMaxIdle(Integer maxIdle) {
		MaxIdle = maxIdle;
	}
	/**
	 * @return the minIdle
	 */
	public Integer getMinIdle() {
		return minIdle;
	}
	/**
	 * @param minIdle the minIdle to set
	 */
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	/**
	 * @return the timeout
	 */
	public Long getTimeout() {
		return timeout;
	}
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	
}

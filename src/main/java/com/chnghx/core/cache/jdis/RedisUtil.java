package com.chnghx.core.cache.jdis;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisUtil {

	@Autowired
	private RedisProperties redisProperties;

	@Autowired
	private JedisPool jedisPool;
	
	@Bean(name= "jedis.pool")  
    @Autowired
    public JedisPool jedisPool(JedisPoolConfig config) {
		String host=redisProperties.getHost();
		Integer port=redisProperties.getPort();
        return new JedisPool(config, host, port);  
    } 
	
	@Bean(name= "jedis.pool.config")  
    public JedisPoolConfig jedisPoolConfig () {  
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(redisProperties.getMaxActive());  
        config.setMaxIdle(redisProperties.getMaxIdle());  
        config.setMaxWaitMillis(redisProperties.getMaxWait());  
        config.setMinIdle(redisProperties.getMinIdle());
        return config;  
    } 
	
	
	private static final int STEP = 100;  

	private Object lock = new Object();

	/**
	 * 
	 * 方法描述: 从redis中取出可用序列
	 * 
	 * @param <PK>
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public <PK extends Serializable> PK getPkByRedis(String table)
			throws Exception {
		String seqName = StringUtils.lowerCase(table);
		// step1 : 从redis中取出序列并更新
		Jedis jedis = jedisPool.getResource();
		Long pk = jedis.incr(seqName);//将键的整数值增加1
//		if (pk == 1 || pk == 0) {
//			// step2 : redis中无序列，从数据库中取出序列并更新数据
//			pk = loadSeqByName(table, STEP);
//			jedis.set(seqName, String.valueOf(pk));
//		}
		jedisPool.returnResource(jedis); 
		// value = Long.valueOf(pk).intValue();
//		if (pk % STEP == 0 && pk > 0) {
//			// 更新数据库
//			boolean result = updateSeq(table, pk);
//			if (!result) {
//				System.out.println("更新数据库异常。。。。。。。。。。。。。。。");
//				throw new Exception(table + "主键获取异常！");
//			}
//			System.out.println("更新数据库成功~~~~~~~~~~~~~~~~~~~");
//		}
		
		return (PK) pk;
	}

	/**
	 * 
	 * 方法描述: 更新数据库中现有主键值
	 * 
	 * @param <PK>
	 * @param pk
	 * @return
	 */
//	private <PK extends Serializable> boolean updateSeq(String seqName, PK pk) {
//		int row = tbSeqMapper.updateBySeqName(String.valueOf(Long.valueOf(String.valueOf(pk)) + STEP), seqName);
//		return row > 0;
//	}

	public <PK extends Serializable> PK getValue(String table) throws Exception {

		 table = table.toLowerCase();
		// Long pkValue = loadSeqByName(table, 1);
		synchronized (lock) {
			PK pkValue = getPkByRedis(table);
			return pkValue;
		}
	}

//	private long loadSeqByName(String name, int size) {
//		List<TbSeq> list = tbSeqMapper.selectBySeqNameList(name);
//		TbSeq seq = null;
//		if (list.isEmpty()) {
//			seq = new TbSeq();
//			seq.setSeqName(name);
//			seq.setSeqValue(size + "");
//			tbSeqMapper.insertSelective(seq);
//			return 1;
//		} else {
//			seq = list.get(0);
//			long value = Long.parseLong(seq.getSeqValue()) + 1;
//			seq = new TbSeq();
//			// 更新数据库主键存储
//			tbSeqMapper.updateBySeqName(String.valueOf(value + size), name);
//			return value;
//		}
//	}
}

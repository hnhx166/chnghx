package com.chnghx.core.cache.redis;

import org.springframework.stereotype.Service;

import com.chnghx.core.cache.CacheService;

@Service
public class RedisCacheServiceImpl<K , V> implements CacheService<K , V>{

//	@Autowired
//    RedisTemplate<K, V> redisTemplate;
//
//	public void put(K key, V value) {
//		redisTemplate.opsForValue().set(key, value);
//		
//	}
//
//	public V get(K key) {
//		return redisTemplate.opsForValue().get(key);
//	}
//
//	public <T> T get(Object key, Class<T> type) {
//		// TODO 自动生成的方法存根
//		return null;
//	}
//
//	public void remove(Object key) {
//		
//	}
	
	
}

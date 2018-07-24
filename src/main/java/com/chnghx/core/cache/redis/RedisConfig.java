package com.chnghx.core.cache.redis;

/*
@Configuration
@EnableCaching*/
public class RedisConfig {
//extends CachingConfigurerSupport {
//	
//	/**
//     * 生成key的策略
//     *
//     * @return
//     */
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//			public Object generate(Object target, Method method, Object... params) {
//				 StringBuilder sb = new StringBuilder();
//	                sb.append(target.getClass().getName());
//	                sb.append(method.getName());
//	                for (Object obj : params) {
//	                    sb.append(obj.toString());
//	                }
//	                return sb.toString();
//			}
//		};
//    }
//    
//    /**
//     * 管理缓存
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @SuppressWarnings("rawtypes")
//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
//        //设置缓存过期时间
//        // rcm.setDefaultExpiration(60);//秒
//        //设置value的过期时间
//        Map<String,Long> map=new HashMap<String, Long>();
//        map.put("test",60L);
//        rcm.setExpires(map);
//        return rcm;
//    }
//    
//    /**
//     * RedisTemplate配置
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//         StringRedisTemplate template = new StringRedisTemplate(factory);
//         Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//         ObjectMapper om = new ObjectMapper();
//         om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//         om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//         jackson2JsonRedisSerializer.setObjectMapper(om);
//         template.setValueSerializer(jackson2JsonRedisSerializer);//如果key是String 需要配置一下StringSerializer,不然key会乱码 /XX/XX
//         template.afterPropertiesSet();
//         return template;
//    }
//    
//    
    
    
    
    
    
	
//	/**
//     * 注入 RedisConnectionFactory
//     */
//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;
//    
//    /**
//     * 实例化 RedisTemplate 对象
//     *
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, Object> getRedisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        initRedisTemplate(redisTemplate, redisConnectionFactory);
//        return redisTemplate;
//    }
//    
//    /**
//     * 设置数据存入 redis 的序列化方式
//     *
//     * @param redisTemplate
//     * @param factory
//     */
//    private void initRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setConnectionFactory(factory);
//    }
//    
//    /**
//     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForHash();
//    }
//    
//    /**
//     * 实例化 ValueOperations 对象,可以使用 String 操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForValue();
//    }
//    
//    /**
//     * 实例化 ListOperations 对象,可以使用 List 操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForList();
//    }
//    
//    /**
//     * 实例化 SetOperations 对象,可以使用 Set 操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForSet();
//    }
//    
//    /**
//     * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForZSet();
//    }
}

package com.chnghx.common.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class HttpPostMethod extends PostMethod{
	public final static String jsonObject = "JSONObject";
	public final static String jsonArray = "JSONArray";
	
	/**
	 * 直接返回对应类型
	 * @param <T>
	 * @param requiredType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T executeMethod(Class<T> requiredType) {
		if (null == requiredType) {
			return null;
		}
		if ("com.alibaba.fastjson.JSONArray".equals(requiredType
				.getCanonicalName())) {
			return (T)executeMethod(jsonArray);
		}
		if ("com.alibaba.fastjson.JSONObject".equals(requiredType
				.getCanonicalName())) {
			return (T)executeMethod(jsonObject);
		}
		return (T) (executeMethod());

	}
	/***
	 * 执行方法
	 * type 可以不传，默认返回String
	 * @param type[JSONObject,JSONArray]
	 * @return
	 */
	public Object executeMethod(String...type){
		HttpClient client = new HttpClient();  
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");  
		client.getHttpConnectionManager()
									.getParams()
										.setConnectionTimeout(1000 * 60); 
		int status = 0 ;
		String result = null;
		String url = getPath();
		try {  
            status = client.executeMethod(this);  
        } catch (Exception e) {  
        	LoggerUtils.error(getClass(), "请求[" + url +"]超时或错误,message : " + e.getMessage(),e);
        }  
        
        if (status == HttpStatus.SC_OK) {  
            try {  
                result = this.getResponseBodyAsString();  
            } catch (IOException e) { 
            	LoggerUtils.error(getClass(), "Abnormal request returns! Exception information as follows: " + e.getMessage(), e);
            }  
        }else{
        	LoggerUtils.error(getClass(), "HTTP请求错误，请求地址为：["+url +"],状态为：["+ status+ "]");
        }
       
        this.releaseConnection();  
        
        if(StringUtils.indexOf(jsonObject, type) > 0){
        	return JSONObject.parseObject(result);
        }
        
        if(StringUtils.indexOf(jsonArray, type) > 0){
        	return JSONArray.parseArray(result);
        }
        return result ;
	}
	/**
	 * 设置参数List<Map<String, Object>>
	 * @param parameter
	 */
	public void setParameter(List<Map<String, Object>> parameter) {
		for (Map<String, Object> map : parameter) {
			setParameter(map);
		}
	}
	
	
	/**
	 * 设置参数Map<String, Object>
	 * @param parameter
	 */
	public void setParameter(Map<String, Object> parameter) {
		for (String key : parameter.keySet()) {
			Object str = parameter.get(key) ;
			String value = null == str?"":StringUtils.trimToEmpty(str.toString());
			this.addParameter(key,value);
		}
	}
	
	
	
	/**
	 * 构造方法begin
	 */
	public HttpPostMethod(String url) {
		super(url);
	}
	
	public HttpPostMethod(List<Map<String, Object>> parameter) {
		super();
		this.setParameter(parameter);
	}
	public HttpPostMethod(String url,List<Map<String, Object>> parameter) {
		super(url);
		this.setParameter(parameter);
	}
	
	public HttpPostMethod(Map<String,Object> parameter) {
		super();
		this.setParameter(parameter);
	}
	public HttpPostMethod(String url,Map<String,Object> parameter) {
		super( url);
		this.setParameter(parameter);
	}
	/**
	 * 构造方法end
	 */
}

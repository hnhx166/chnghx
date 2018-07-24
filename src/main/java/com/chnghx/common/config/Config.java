package com.chnghx.common.config;

import java.io.IOException;
import java.util.Properties;

import com.chnghx.common.utils.LoggerUtils;

public class Config {

	/**
	 * 配置文件
	 */
	private static Properties prop = null;
	
	
	/**
	 * 配置文件名称
	 */
	private final static String FILE_NAME = "/config.properties";
	
	/**
	 * 配置文件中的key
	 */
	public static final String IMG_VINUXPOST_WIDTHPX = "img.vinuxpost.widthpx";
	public static final String IMG_VINUXPOST_HEIGHTPX = "img.vinuxpost.heightpx";
	
	/**
	 * 图片来源
	 */
	public static final String SYSTEM_TYPE = "systemType";

	private Config() {
	}
	static{
		prop = new Properties();
		try {
			prop.load(Config.class.getResourceAsStream(FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key){
		String value = prop.getProperty(key);
		LoggerUtils.debug(Config.class, String.format("config.properties.通过Key[%s],获取到的Value[%s]", key,value));
		return value;
	}
}

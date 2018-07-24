package com.chnghx.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公共静态方法
 * 
 */
public class Tools {
	private static final Logger LOG = LoggerFactory.getLogger(Tools.class);

	/**
	 * 根据文件路径获取文件名
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 文件名
	 */
	public static String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}

	/**
	 * 获取文件后缀 如.jpg
	 * 
	 * @param originalFilename
	 * @return
	 */
	public static String getFileSuffix(String originalFilename) {
		LOG.debug("获得上传文件名:", originalFilename);
		String res = "";
		if (StringUtils.isNotBlank(originalFilename)) {
			res = originalFilename.substring(originalFilename.lastIndexOf("."))
					.toLowerCase();
		}
		return res;
	}
}

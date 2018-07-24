package com.chnghx.web.hdfs.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.chnghx.common.base.APIResult;
import com.chnghx.common.config.Config;
import com.chnghx.common.utils.ImageUtils;
import com.chnghx.common.utils.LoggerUtils;
import com.chnghx.common.utils.StringUtils;
import com.chnghx.common.utils.Tools;

@Controller
@RequestMapping("upload")
public class UploadController {
	
	/**
	 * 跳转上传实例页面
	 * 
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {

		return new ModelAndView("upload/index");
	}
	
	/**
     * 实现文件上传
     * */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file){
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        
        String path = "F:/test" ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "true";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }
    
    /*
     * 获取multifile.html页面
     */
    @RequestMapping("multifile")
    public String multifile(){
        return "/multifile";
    }
    
    /**
     * 实现多文件上传
     * */
    @RequestMapping(value="multifileUpload",method=RequestMethod.POST) 
    public @ResponseBody String multifileUpload(HttpServletRequest request){
    	/**public @ResponseBody String multifileUpload(@RequestParam("fileName")List<MultipartFile> files) */
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");
        
        if(files.isEmpty()){
            return "false";
        }

        String path = "F:/test" ;
        
        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);
            
            if(file.isEmpty()){
                return "false";
            }else{        
                File dest = new File(path + "/" + fileName);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "false";
                } 
            }
        }
        return "true";
    }
    
    
    @RequestMapping("download")
    public String downLoad(HttpServletResponse response){
        String filename="2.jpg";
        String filePath = "F:/test" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
            
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file); 
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

	/**
	 * 从页面上传文件
	 * 由于此框架封装request为shirorequest,所以这里用DefaultMultipartHttpServletRequest作为参数
	 * ,接收文件参数 入参：
	 * 
	 * @param request
	 *            (file,systemType)
	 * @param response
	 *            出参： json(fileName,status,message)
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(DefaultMultipartHttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> resultMap = new HashMap<String, String>();
		String systemType = request.getParameter("systemType");
		LoggerUtils.fmtDebug(getClass(), "系统类别：" + systemType, "");
		String uploadurl = "";

		if (systemType != null && !"".equals(systemType)) {
			uploadurl = Config.getProperty("img." + systemType + ".url");
			if (uploadurl == null) {
				LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");

				return JSON.toJSONString(new APIResult<Map<String, String>>(
						300, "the system type is not supported!", resultMap));
			}
		} else {
			LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,
					"the system type is not supported!", resultMap));
		}

		MultiValueMap<String, MultipartFile> fileMap = request
				.getMultiFileMap();
		List<MultipartFile> mutFileList = null;
		MultipartFile mutFile = null;
		for (String fileName : fileMap.keySet()) {
			mutFileList = fileMap.get(fileName);
			if (mutFileList != null && mutFileList.size() > 0) {
				mutFile = mutFileList.get(0);
			}
			break;// 每次只能上传一张图片
		}

		String name = "";// 文件名
		if (null != mutFile && mutFile.getOriginalFilename() != null) {
			name = mutFile.getOriginalFilename();
		} else {
			LoggerUtils.fmtDebug(getClass(), "上传图片为空,请选择图片!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,
					" the image is null !", resultMap));
		}

		String suffix = null;
		if (null != name && !"".equals(name)) {
			suffix = Tools.getFileSuffix(name);
		}

		LoggerUtils.fmtDebug(getClass(), "上传文件格式为：" + suffix, "");
		long size = mutFile.getSize();
		size = size / 1024;

		if (size > 2048) {// 图片大小限制
			LoggerUtils.fmtDebug(getClass(), "上传图片大小：" + size + "KB", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//图片超过2MB的大小！
					"\u56FE\u7247\u8D85\u8FC72MB\u7684\u5927\u5C0F\uFF01", resultMap));
		} else if (size < 5) {
			LoggerUtils.fmtDebug(getClass(), "上传图片大小：" + size + "KB", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//图片质量不足5KB，请选择稍好一点的图片！
					"\u56FE\u7247\u8D28\u91CF\u4E0D\u8DB35KB\uFF0C\u8BF7\u9009\u62E9\u7A0D\u597D\u4E00\u70B9\u7684\u56FE\u7247\uFF01", resultMap));
		}
		// 支持png,gif,jpg,bmp
		if (suffix == null
				|| (!suffix.equals(".jpg") && !suffix.equals(".gif")
						&& !suffix.equals(".png") && !suffix.equals(".bmp")
						&& !suffix.equals(".JPG") && !suffix.equals(".GIF")
						&& !suffix.equals(".PNG") && !suffix.equals(".BMP")
						&& !suffix.equals(".jpeg") && !suffix.equals(".JPEG"))) {

			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//不支持的格式：   
					"\u4E0D\u652F\u6301\u7684\u683C\u5F0F\uFF1A" + suffix + "%3B\u6700\u597D\u8BF7\u9009\u62E9jpg\u683C\u5F0F\u7684\u56FE\u7247\uFF01", resultMap));
		}
		InputStream inputStream = null;
		try {
			inputStream = mutFile.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			LoggerUtils.fmtDebug(getClass(), "获取文件流失败!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//获取文件流失败!
					"\u83B7\u53D6\u6587\u4EF6\u6D41\u5931\u8D25%21", resultMap));
		}
		BufferedImage bufImg = null;
		try {
			bufImg = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			LoggerUtils.fmtDebug(getClass(), "读取图片异常，异常原因：" + e.getMessage(),
					"");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//读取图片异常!
					"\u8BFB\u53D6\u56FE\u7247\u5F02\u5E38%21", resultMap));
		}
		long time = System.currentTimeMillis(); // 使用时间作为文件名
		name = time + suffix;
		// images/touxiang/2.jpg
		suffix = suffix.replace(".", ""); // 去掉点，以便后面保存图片格式,jpg/png/gif
		// 获取上传图片像素
		int width = bufImg.getWidth();
		int height = bufImg.getHeight();
		// 获取标准图片像素配置
		String widthpx = Config.getProperty("img.vinuxpost.widthpx");
		String heightpx = Config.getProperty("img.vinuxpost.heightpx");
		/*
		 * 自定义上传图片大小判断 baohui.LU update 2014/11/03
		 */
		String imgWidth = request.getParameter("width");
		String imgHeight = request.getParameter("height");
		LoggerUtils.fmtDebug(getClass(), imgWidth + ":" + imgHeight, "");
		if (StringUtils.isNotBlank(imgWidth)
				&& StringUtils.isNotBlank(imgHeight)) {
			widthpx = imgWidth;
			heightpx = imgHeight;
		}

		if ("vinuxpost".equals(systemType)) { // vinuxpost对图片有限制
			if (Integer.valueOf(widthpx) == width
					&& Integer.valueOf(heightpx) == height) {// 上传图片符合标准
				LoggerUtils.fmtDebug(getClass(), "图片存储目录为：" + uploadurl + name,
						"");
				// 图片缩放操作
				String url = ImageUtils.save(suffix, name, uploadurl,
						bufImg.getWidth(), bufImg.getHeight(), bufImg);
				LoggerUtils.fmtDebug(getClass(), "上传图片返回url：" + url, "");
				resultMap.put("fileName", name);
				return JSON.toJSONString(new APIResult<Map<String, String>>(
						200, "success", resultMap));
			} else {
				LoggerUtils.fmtDebug(getClass(), "请上传像素为" + widthpx + "*"
						+ heightpx + "的图片!");
				return JSON.toJSONString(new APIResult<Map<String, String>>(//请上传像素为           的图片!
						300, "\u8BF7\u4E0A\u4F20\u50CF\u7D20\u4E3A" + widthpx + "*" + heightpx + "\u7684\u56FE\u7247%21",
						resultMap));
			}
		} else {// 别的系统暂时对图片没有限制,并且不进行缩放操作
			LoggerUtils.fmtDebug(getClass(), "图片存储目录为：" + uploadurl + name, "");
			String url = ImageUtils.save(suffix, name, uploadurl,
					bufImg.getWidth(), bufImg.getHeight(), bufImg);
			LoggerUtils.fmtDebug(getClass(), "上传图片返回url：" + url, "");
			resultMap.put("fileName", name);

			return JSON.toJSONString(new APIResult<Map<String, String>>(200,
					"success", resultMap));
		}
	}

	/**
	 * 封装接口 客户端通过接口方式上传图片，而非页面;此方法为接口，供其他系统后台直接调用 入参：
	 * 
	 * @param request
	 *            (systemType,io)
	 * @param response
	 *            出参： json(fileName,status,message)
	 * 
	 *            注：每次只能上传一张
	 */
	@RequestMapping(value = "ioUpload", method = RequestMethod.POST)
	@ResponseBody
	public String ioUpload(HttpServletRequest request,
			HttpServletResponse response) {
		LoggerUtils.fmtDebug(getClass(), "通过接口，开始上传图片!", "");
		Map<String, String> resultMap = new HashMap<String, String>();
		String uploadPath = ""; // 上传文件的目录
		String systemType = request.getParameter("systemType");

		if (systemType != null && !"".equals(systemType)) {
			uploadPath = Config.getProperty("img." + systemType + ".url");
			if (uploadPath == null) {
				LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
				return JSON.toJSONString(new APIResult<Map<String, String>>(
						300, "the system type is not supported!", resultMap));
			}
		} else {
			LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,
					"the system type is not supported!", resultMap));
		}

		File uploadFile = new File(uploadPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		try {
			String name = request.getParameter("name");
			String suffix = null;
			if (null != name && !"".equals(name)) {
				suffix = Tools.getFileSuffix(name);
			}

			if (".jpg".equals(suffix) || ".JPG".equals(suffix)
					|| ".bmp".equals(suffix) || ".BMP".equals(suffix)
					|| ".png".equals(suffix) || ".PNG".equals(suffix)
					|| ".jpeg".equals(suffix) || ".JPGE".equals(suffix)) {

				String aa = request.getParameter("io");
				if (aa == null) {
					resultMap.put("fileName", "");
					return JSON
							.toJSONString(new APIResult<Map<String, String>>(
									500, "上传失败", resultMap));
				}
				byte[] ab = aa.getBytes("ISO8859-1");

				String size = "";
				DecimalFormat df2 = new DecimalFormat("###.00");// 这样为保持2位
				size = df2.format(ab.length / 1024) + "kb";

				LoggerUtils.fmtDebug(getClass(), "通过io上传图片，图片通过计算，大小为:" + size,
						"");
				InputStream in = new ByteArrayInputStream(ab);

				BufferedImage bufImg = null;
				try {
					bufImg = ImageIO.read(in);// 如果此处仍为null，httpclient请求需要设置请求头的编码，utf-8
					in.close();
				} catch (IOException e) {

					e.printStackTrace();
					LoggerUtils.fmtDebug(getClass(),
							"读取图片异常，异常原因：" + e.getMessage(), "");
					return JSON
							.toJSONString(new APIResult<Map<String, String>>(
									300, "读取图片异常，异常原因!", resultMap));
				}
				long time = System.currentTimeMillis(); // 使用时间作为文件名
				name = time + suffix; // 生成新文件名

				suffix = suffix.replace(".", ""); // 去掉点，以便后面保存图片格式,jpg/png/gif

				String url = ImageUtils.save(suffix, name, uploadPath,
						bufImg.getWidth(), bufImg.getHeight(), bufImg);// 后缀，参数不带.
																		// 例如：jpg/png
				if (!"".equals(url) && null != url) {
					LoggerUtils.fmtDebug(getClass(), "上传图片返回url：" + url, "");
					resultMap.put("fileName", name);

					return JSON
							.toJSONString(new APIResult<Map<String, String>>(
									200, "success", resultMap));
				} else {
					resultMap.put("fileName", "");
					return JSON
							.toJSONString(new APIResult<Map<String, String>>(//上传失败
									500, "\u4E0A\u4F20\u5931\u8D25", resultMap));
				}

			} else {
				resultMap.put("fileName", "");
				return JSON.toJSONString(new APIResult<Map<String, String>>(
						500, "image type is not supported!", resultMap));
			}

		} catch (Exception e) {
			// 可以跳转出错页面
			e.printStackTrace();
			LoggerUtils.fmtDebug(getClass(), "上传图片出现异常!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(500,//上传图片出现异常!
					"\u4E0A\u4F20\u56FE\u7247\u51FA\u73B0\u5F02\u5E38%21", resultMap));
		}
	}

	/**
	 * 封装接口 客户端通过接口方式上传图片，而非页面;此方法为接口，供其他系统后台直接调用 入参：
	 * 
	 * @param request
	 *            (systemType,urlPath)
	 * @param response
	 *            出参： json(fileName,status,msg) 注：每次只能上传一张
	 */
	@RequestMapping(value = "webUpload", method = RequestMethod.POST)
	@ResponseBody
	public APIResult<Map<String, String>> webUpload(HttpServletRequest request,
			HttpServletResponse response) {
		LoggerUtils.fmtDebug(getClass(), "通过接口，开始上传图片!", "");
		Map<String, String> resultMap = new HashMap<String, String>();
		String uploadPath = ""; // 上传文件的目录
		String systemType = request.getParameter("systemType");

		if (systemType != null && !"".equals(systemType)) {
			uploadPath = Config.getProperty("img." + systemType + ".url");
			if (uploadPath == null) {
				LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
				return new APIResult<Map<String, String>>(300,
						"the system type is not supported!", resultMap);
			}
		} else {
			LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
			return new APIResult<Map<String, String>>(300,
					"the system type is not supported!", resultMap);
		}

		File uploadFile = new File(uploadPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		try {

			String urlPath = request.getParameter("urlPath");
			String fileName = "";
			if (urlPath != null && urlPath.contains(".")) {
				fileName = Tools.getFileName(urlPath);
			}

			String name = fileName;
			String suffix = null;
			if (null != name && !"".equals(name)) {
				suffix = Tools.getFileSuffix(name);
			}

			if (".jpg".equals(suffix) || ".JPG".equals(suffix)
					|| ".bmp".equals(suffix) || ".BMP".equals(suffix)
					|| ".png".equals(suffix) || ".PNG".equals(suffix)
					|| ".jpeg".equals(suffix) || ".JPGE".equals(suffix)) {

				URL url = null;
				try {
					url = new URL(urlPath);
				} catch (MalformedURLException e) {

					LoggerUtils.fmtDebug(getClass(),
							"网络地址获取有误:" + e.getMessage(), "");
					e.printStackTrace();
					return new APIResult<Map<String, String>>(300, "网络地址获取有误!",
							resultMap);
				}

				HttpURLConnection httpConn = (HttpURLConnection) url
						.openConnection();
				httpConn.connect();
				InputStream cin = httpConn.getInputStream();

				BufferedImage bufImg = null;
				try {
					bufImg = ImageIO.read(cin);
					cin.close();
				} catch (IOException e) {
					LoggerUtils.fmtDebug(getClass(),
							"读取图片异常，异常原因：" + e.getMessage(), "");
					e.printStackTrace();
					return new APIResult<Map<String, String>>(300, "读取图片异常!",
							resultMap);
				}
				long time = System.currentTimeMillis(); // 使用时间作为文件名
				name = time + suffix; // 生成新文件名
				suffix = suffix.replace(".", ""); // 去掉点，以便后面保存图片格式,jpg/png/gif

				String uri = ImageUtils.save(suffix, name, uploadPath,
						bufImg.getWidth(), bufImg.getHeight(), bufImg);// 后缀，参数不带.
																		// 例如：jpg/png
				if (!"".equals(uri) && null != uri) {
					LoggerUtils.fmtDebug(getClass(), "上传图片返回url：" + uri, "");
					resultMap.put("fileName", name);
					return new APIResult<Map<String, String>>(200, "success",
							resultMap);

				} else {
					resultMap.put("fileName", "");
					return new APIResult<Map<String, String>>(500, "\u4E0A\u4F20\u5931\u8D25",//上传失败
							resultMap);
				}

			} else {
				resultMap.put("fileName", "");
				return new APIResult<Map<String, String>>(500,
						"image type is not supported!", resultMap);
			}
		} catch (Exception e) {
			// 可以跳转出错页面
			e.printStackTrace();
			LoggerUtils.fmtDebug(getClass(), "图片上传出现异常!", "");
			return new APIResult<Map<String, String>>(500, "\u56FE\u7247\u4E0A\u4F20\u51FA\u73B0\u5F02\u5E38%21",//图片上传出现异常!
					resultMap);
		}
	}

	/**
	 * 页面上传 自定义宽高上传图片 入参：
	 * 
	 * @param request
	 *            (systemType,width,height)
	 * @param response
	 *            出参： json(fileName,status,msg)
	 */
	@RequestMapping(value = "fixUpload", method = RequestMethod.POST)
	@ResponseBody
	public String fixUpload(DefaultMultipartHttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String systemType = request.getParameter("systemType");

		LoggerUtils.fmtDebug(getClass(), "系统类别：" + systemType, "");

		String widthStr = request.getParameter("width");
		String heightStr = request.getParameter("height");

		int width = 0;
		int height = 0;
		if (widthStr != null && !"".equals(widthStr) && heightStr != null
				&& !"".equals(heightStr)) {
			width = Integer.valueOf(widthStr);
			height = Integer.valueOf(heightStr);
		} else {
			LoggerUtils.fmtDebug(getClass(), "未传入图片宽和高!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,
					"the width and height is null !", resultMap));
		}

		String uploadurl = "";

		if (systemType != null && !"".equals(systemType)) {
			uploadurl = Config.getProperty("img." + systemType + ".url");
			if (uploadurl == null) {
				LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
				return JSON.toJSONString(new APIResult<Map<String, String>>(
						300, "the system type is not supported!", resultMap));
			}
		} else {
			LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,
					"the system type is not supported!", resultMap));
		}

		MultiValueMap<String, MultipartFile> fileMap = request
				.getMultiFileMap();
		List<MultipartFile> mutFileList = null;
		MultipartFile mutFile = null;
		for (String fileName : fileMap.keySet()) {
			mutFileList = fileMap.get(fileName);
			if (mutFileList != null && mutFileList.size() > 0) {
				mutFile = mutFileList.get(0);
			}
			break;// 每次只能上传一张图片
		}

		String name = "";// 文件名
		if (null != mutFile && mutFile.getOriginalFilename() != null) {
			name = mutFile.getOriginalFilename();
		} else {
			LoggerUtils.fmtDebug(getClass(), "上传图片为空,请选择图片!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//上传图片为空!
					"\u4E0A\u4F20\u56FE\u7247\u4E3A\u7A7A%21", resultMap));
		}

		String suffix = null;
		if (null != name && !"".equals(name)) {
			suffix = Tools.getFileSuffix(name);
		}

		LoggerUtils.fmtDebug(getClass(), "上传文件格式为：" + suffix, "");
		long size = mutFile.getSize();
		size = size / 1024;
		if (size > 2048) {// 图片大小限制
			LoggerUtils.fmtDebug(getClass(), "上传图片大小：" + size + "KB", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//图片超过2MB的大小！
					"\u56FE\u7247\u8D85\u8FC72MB\u7684\u5927\u5C0F\uFF01", resultMap));

		} else if (size < 5) {
			LoggerUtils.fmtDebug(getClass(), "上传图片大小：" + size + "KB", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//图片质量不足5KB，请选择稍好一点的图片！
					"\u56FE\u7247\u8D28\u91CF\u4E0D\u8DB35KB\uFF0C\u8BF7\u9009\u62E9\u7A0D\u597D\u4E00\u70B9\u7684\u56FE\u7247\uFF01", resultMap));
		}
		// 支持png,gif,jpg,bmp
		if (suffix == null
				|| (!suffix.equals(".jpg") && !suffix.equals(".gif")
						&& !suffix.equals(".png") && !suffix.equals(".bmp")
						&& !suffix.equals(".JPG") && !suffix.equals(".GIF")
						&& !suffix.equals(".PNG") && !suffix.equals(".BMP")
						&& !suffix.equals(".jpeg") && !suffix.equals(".JPEG"))) {
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//不支持的格式：               ;最好请选择jpg格式的图片！
					"\u4E0D\u652F\u6301\u7684\u683C\u5F0F\uFF1A" + suffix + "%3B\u6700\u597D\u8BF7\u9009\u62E9jpg\u683C\u5F0F\u7684\u56FE\u7247\uFF01", resultMap));
		}
		InputStream inputStream = null;
		try {
			inputStream = mutFile.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			LoggerUtils.fmtDebug(getClass(), "获取文件流失败!", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//获取文件流失败!
					"\u83B7\u53D6\u6587\u4EF6\u6D41\u5931\u8D25%21", resultMap));
		}
		BufferedImage bufImg = null;
		try {
			bufImg = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();

			LoggerUtils.fmtDebug(getClass(), "读取图片异常，异常原因：" + e.getMessage(),
					"");
			return JSON.toJSONString(new APIResult<Map<String, String>>(300,//读取图片异常!
					"\u8BFB\u53D6\u56FE\u7247\u5F02\u5E38%21", resultMap));

		}
		long time = System.currentTimeMillis(); // 使用时间作为文件名
		name = time + suffix;

		// images/touxiang/2.jpg
		suffix = suffix.replace(".", ""); // 去掉点，以便后面保存图片格式,jpg/png/gif

		/*
		 * 自定义大小上传图片
		 */
		LoggerUtils.fmtDebug(getClass(), "图片存储目录为：" + uploadurl + name, "");
		String url = ImageUtils.save(suffix, name, uploadurl, width, height,
				bufImg);
		LoggerUtils.fmtDebug(getClass(), "上传图片返回url：" + url, "");
		if (url != null && !"".equals(url)) {
			resultMap.put("fileName", name);
			return JSON.toJSONString(new APIResult<Map<String, String>>(200,
					"success", resultMap));
		} else {
			resultMap.put("fileName", "");
			return JSON.toJSONString(new APIResult<Map<String, String>>(500,//图片生成失败
					"\u56FE\u7247\u751F\u6210\u5931\u8D25", resultMap));
		}
	}

	/**
	 * 从页面上传 一张图片生成多张大小固定的图片 200*200,300*300,400*400,500*500
	 * 
	 * 入参：
	 * 
	 * @param request
	 *            (systemType,width,height)
	 * @param response
	 *            出参： json(fileName_宽,status_宽,status,msg)
	 * 
	 */
	@RequestMapping(value = "uploadFour", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFour(DefaultMultipartHttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> esMap = new HashMap<String, Object>();

		String systemType = request.getParameter("systemType");

		esMap.put("sourceType", systemType);// 图片来源

		// systemType = "vinuxpost";
		LoggerUtils.fmtDebug(getClass(), "系统类别：" + systemType, "");

		String uploadurl = "";
		if (systemType != null && !"".equals(systemType)) {
			uploadurl = Config.getProperty("img." + systemType + ".url");
			if (uploadurl == null) {
				LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
				return JSON.toJSONString(new APIResult<Map<String, Object>>(
						300, "the system type is not supported!", resultMap));
			}
		} else {
			LoggerUtils.fmtDebug(getClass(), "系统未识别!", "");
			return JSON.toJSONString(new APIResult<Map<String, Object>>(300,
					"the system type is not supported!", resultMap));
		}

		MultiValueMap<String, MultipartFile> fileMap = request
				.getMultiFileMap();
		List<MultipartFile> mutFileList = null;
		MultipartFile mutFile = null;
		for (String fileName : fileMap.keySet()) {
			mutFileList = fileMap.get(fileName);
			if (mutFileList != null && mutFileList.size() > 0) {
				mutFile = mutFileList.get(0);
			}
			break;// 每次只能上传一张图片
		}

		String name = "";// 文件名
		if (null != mutFile && mutFile.getOriginalFilename() != null) {
			name = mutFile.getOriginalFilename();
		} else {
			LoggerUtils.fmtDebug(getClass(), "上传图片为空,请选择图片!", "");
			return JSON.toJSONString(new APIResult<Map<String, Object>>(300,//上传图片为空!
					"\u4E0A\u4F20\u56FE\u7247\u4E3A\u7A7A%21", resultMap));
		}

		String suffix = null;
		if (null != name && !"".equals(name)) {
			suffix = Tools.getFileSuffix(name);
		}
		esMap.put("imageSuffix", suffix);

		LoggerUtils.fmtDebug(getClass(), "上传文件格式为：" + suffix, "");
		long size = mutFile.getSize();
		size = size / 1024;
		esMap.put("imageSize", size + "kb");

		if (size > 2048) {// 图片大小限制
			LoggerUtils.fmtDebug(getClass(), "上传图片大小：" + size + "KB", "");
			return JSON.toJSONString(new APIResult<Map<String, Object>>(300,//图片超过2MB的大小！
					"\u56FE\u7247\u8D85\u8FC72MB\u7684\u5927\u5C0F\uFF01", resultMap));
		} else if (size < 5) {
			LoggerUtils.fmtDebug(getClass(), "上传图片大小：" + size + "KB", "");
			return JSON.toJSONString(new APIResult<Map<String, Object>>(300,//图片质量不足5KB！
					"\u56FE\u7247\u8D28\u91CF\u4E0D\u8DB35KB\uFF01", resultMap));
		}
		// 支持png,gif,jpg,bmp,jpeg
		if (suffix == null
				|| (!suffix.equals(".jpg") && !suffix.equals(".gif")
						&& !suffix.equals(".png") && !suffix.equals(".bmp")
						&& !suffix.equals(".JPG") && !suffix.equals(".GIF")
						&& !suffix.equals(".PNG") && !suffix.equals(".BMP")
						&& !suffix.equals(".jpeg") && !suffix.equals(".JPEG"))

		) {
			return JSON.toJSONString(new APIResult<Map<String, Object>>(300,//不支持的格式：                                 ;最好请选择jpg格式的图片！
					"\u4E0D\u652F\u6301\u7684\u683C\u5F0F\uFF1A" + suffix + "%3B\u6700\u597D\u8BF7\u9009\u62E9jpg\u683C\u5F0F\u7684\u56FE\u7247\uFF01", resultMap));
		}
		InputStream inputStream = null;
		try {
			inputStream = mutFile.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			LoggerUtils.fmtDebug(getClass(), "获取文件流失败!", "");
			return JSON.toJSONString(new APIResult<Map<String, Object>>(300,//获取文件流失败！
					"\u83B7\u53D6\u6587\u4EF6\u6D41\u5931\u8D25\uFF01", resultMap));
		}
		BufferedImage bufImg = null;
		try {
			bufImg = ImageIO.read(inputStream);
		} catch (IOException e) {
			LoggerUtils.fmtDebug(getClass(), "读取图片异常，异常原因：" + e.getMessage(),
					"");
			e.printStackTrace();

			return JSON.toJSONString(new APIResult<Map<String, Object>>(300,//读取图片异常!
					"\u8BFB\u53D6\u56FE\u7247\u5F02\u5E38%21", resultMap));
		}
		long time = System.currentTimeMillis(); // 使用时间作为文件名
		name = time + suffix;

		// suffix = suffix.replace(".", "");
		String newsuffix = suffix.replace(".", "");// 去掉点，以便后面保存图片格式,jpg/png/gif

		/*
		 * 自定义大小上传图片
		 */
		LoggerUtils.fmtDebug(getClass(), "图片存储目录为：" + uploadurl + name, "");

		int[] widths = { 200, 300, 400, 500 };
		int[] heights = { 200, 300, 400, 500 };
		boolean succ = true;
		// Map<String,String> imgMap = new HashMap<String,String>();
		// 同时生成四种大小图片
		for (int i = 0; i < 4; i++) {
			long timemillis = System.currentTimeMillis(); // 使用时间作为文件名
			String newname = timemillis + suffix;
			String url = ImageUtils.save(newsuffix, newname, uploadurl,
					widths[i], heights[i], bufImg);
			LoggerUtils.fmtDebug(getClass(), "上传图片返回url：" + url, "");
			if (url != null && !"".equals(url)) {
				resultMap.put("fileName_" + widths[i], newname);
				resultMap.put("status_" + widths[i], "200");
			} else {
				succ = false;
				resultMap.put("fileName_" + widths[i], "");
				resultMap.put("status_" + widths[i], "500");
			}
		}
		if (succ) {
			return JSON.toJSONString(new APIResult<Map<String, Object>>(200,
					"success", resultMap));
		} else {
			return JSON.toJSONString(new APIResult<Map<String, Object>>(500,
					"failed", resultMap));
		}

	}

	/**
	 * 访问图片,GET请求 入参：系统类型、图片名称、缩放的宽和高 回参：图片流，页面可直接显示
	 * 
	 * @throws IOException
	 *             测试方法，如果img标签无法显示图片，则在地址栏访问即可，看返回什么值
	 */
	@RequestMapping(value = "accessImg/{system}/{fileName}/{width}/{height}", method = RequestMethod.GET)
	public void getImg(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("system") String system,
			@PathVariable("fileName") String fileName,
			@PathVariable("width") int width, @PathVariable("height") int height)
			throws IOException {
		LoggerUtils.fmtDebug(getClass(), "访问图片开始，访问系统代码：" + system, "");
		String filepath = "";
		BufferedImage bi = null;
		if (system != null && fileName != null) {
			// 根据系统类型获取图片路径
			filepath = Config.getProperty("img." + system + ".url");
			if (filepath != null) {
				filepath += fileName;
			} else {
				LoggerUtils.fmtDebug(getClass(), "访问图片，传入的系统类型不支持!传入类型："
						+ system, "");
				response.setHeader("Content-type", "text/html;charset=utf-8");
				// 使用OutputStream字节流进行数据的输出
				response.getOutputStream().write(
						"the system type is not found".getBytes());
				return;
			}

			File file = new File(filepath);
			if (file.exists()) {
				// 图片存在，获取格式
				String suffix = Tools.getFileSuffix(fileName);
				try {
					if (width >= 0 && height >= 0) {
						// 进行图片缩放
						bi = ImageUtils.equalScale(filepath, width, height);

						response.setContentType("image/jpeg");
						response.setHeader("Pragma", "no-cache");
						response.setHeader("Cache-Control", "no-cache");
						response.setIntHeader("Expires", -1);
						try {
							LoggerUtils.fmtDebug(getClass(),
									"访问图片成功,systemType:" + system
											+ ",imageName:" + fileName, "");
							// 此处要根据图片格式进行生成
							if (suffix.equals(".PNG") || suffix.equals(".png")) {
								ImageIO.write(bi, "PNG",
										response.getOutputStream());
							} else if (suffix.equals(".JPG")
									|| suffix.equals(".JPEG")
									|| suffix.equals(".jpg")
									|| suffix.equals(".jpeg")) {
								ImageIO.write(bi, "JPEG",
										response.getOutputStream());
							} else if (suffix.equals(".BMP")
									|| suffix.equals(".bmp")) {
								ImageIO.write(bi, "BMP",
										response.getOutputStream());
							} else {
								ImageIO.write(bi, "JPEG",
										response.getOutputStream());
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						LoggerUtils.fmtDebug(getClass(),
								"the widht or height is not a number", "");
						response.setHeader("Content-type",
								"text/html;charset=utf-8");
						// 使用OutputStream字节流进行数据的输出
						response.getOutputStream().write(
								"the widht or height is not a number"
										.getBytes());
						return;
					}

				} catch (Exception ee) {
					ee.printStackTrace();
					LoggerUtils.fmtDebug(getClass(), "访问出现异常", "");
					response.setHeader("Content-type",
							"text/html;charset=utf-8");
					// 使用OutputStream字节流进行数据的输出
					response.getOutputStream().write("访问出现异常".getBytes());
					return;
				}
			} else {
				LoggerUtils.fmtDebug(getClass(), "the image is not found", "");
				response.setHeader("Content-type", "text/html;charset=utf-8");
				// 使用OutputStream字节流进行数据的输出
				response.getOutputStream().write(
						"the image is not found".getBytes());
				return;
			}
		}

	}

	/**
	 * 获取图片验证码
	 * 
	 * @throws IOException
	 *             type：number/english/numeng 生成验证码时，已存入会话中，校验时从会话中获取
	 */
	@RequestMapping(value = "accessCode/{type}", method = RequestMethod.GET)
	public void getCode(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("type") String type)
			throws IOException {
		if (type != null && !"".equals(type)) {
			ImageUtils.getYZM(response, type);
		} else {
			System.out.println("传入类型不对");
		}
	}


	public static void main(String[] args) {
		
	}
}

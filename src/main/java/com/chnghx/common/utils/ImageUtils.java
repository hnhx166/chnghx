package com.chnghx.common.utils;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;


/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * @author Administrator
 */
public class ImageUtils {
	private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);
	private static BufferedImage subImg;
    /**
     * 几种常见的图片格式
     */
    public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

	// 上传图片格式
	public final static String[] IMAGE_UPLOAD_TYPE = { IMAGE_TYPE_JPG,
			IMAGE_TYPE_GIF, IMAGE_TYPE_PNG, IMAGE_TYPE_BMP, IMAGE_TYPE_JPEG };

    /**
     * 程序入口：用于测试
     * @param args
     */
    public static void main(String[] args) {
        // 1-缩放图像：
        // 方法一：按比例缩放
        ImageUtils.scale("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);//测试OK
        // 方法二：按高度和宽度缩放
        ImageUtils.scale2("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300, true);//测试OK


        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
        ImageUtils.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400 );//测试OK
        // 方法二：指定切片的行数和列数
        ImageUtils.cut2("e:/abc.jpg", "e:/", 2, 2 );//测试OK
        // 方法三：指定切片的宽度和高度
        ImageUtils.cut3("e:/abc.jpg", "e:/", 300, 300 );//测试OK


        // 3-图像类型转换：
        ImageUtils.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");//测试OK


        // 4-彩色转黑白：
        ImageUtils.gray("e:/abc.jpg", "e:/abc_gray.jpg");//测试OK


        // 5-给图片添加文字水印：
        // 方法一：
        ImageUtils.pressText("我是水印文字","e:/abc.jpg","e:/abc_pressText.jpg","宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);//测试OK
        // 方法二：
        ImageUtils.pressText2("我也是水印文字", "e:/abc.jpg","e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
        
        // 6-给图片添加图片水印：
        ImageUtils.pressImage("e:/abc2.jpg", "e:/abc.jpg","e:/abc_pressImage.jpg", 0, 0, 0.5f);//测试OK
    }


    /**
     * 缩放图像（按比例缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param scale 缩放比例
     * @param flag 缩放选择:true 放大; false 缩小;
     */
    public final static void scale(String srcImageFile, String result,
            int scale, boolean flag) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            if (flag) {// 放大
                width = width * scale;
                height = height * scale;
            } else {// 缩小
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 缩放图像（按高度和宽度缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
     */
    public final static void scale2(String srcImageFile, String result, int height, int width, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform
                        .getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 图像切割(按指定起点坐标和宽高切割)
     * @param srcImageFile 源图像地址
     * @param result 切片后的图像地址
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param width 目标切片宽度
     * @param height 目标切片高度
     */
    public final static void cut(String srcImageFile, String result,
            int x, int y, int width, int height) {
        try {
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(),
                                cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 图像切割（指定切片的行数和列数）
     * @param srcImageFile 源图像地址
     * @param descDir 切片目标文件夹
     * @param rows 目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols 目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public final static void cut2(String srcImageFile, String descDir,
            int rows, int cols) {
        try {
            if(rows<=0||rows>20) rows = 2; // 切片行数
            if(cols<=0||cols>20) cols = 2; // 切片列数
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                int destWidth = srcWidth; // 每张切片的宽度
                int destHeight = srcHeight; // 每张切片的高度
                // 计算切片的宽度和高度
                if (srcWidth % cols == 0) {
                    destWidth = srcWidth / cols;
                } else {
                    destWidth = (int) Math.floor(srcWidth / cols) + 1;
                }
                if (srcHeight % rows == 0) {
                    destHeight = srcHeight / rows;
                } else {
                    destHeight = (int) Math.floor(srcWidth / rows) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
                                destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                new FilteredImageSource(image.getSource(),
                                        cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth,
                                destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(descDir
                                + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 图像切割（指定切片的宽度和高度）
     * @param srcImageFile 源图像地址
     * @param descDir 切片目标文件夹
     * @param destWidth 目标切片宽度。默认200
     * @param destHeight 目标切片高度。默认150
     */
    public final static void cut3(String srcImageFile, String descDir,
            int destWidth, int destHeight) {
        try {
            if(destWidth<=0) destWidth = 200; // 切片宽度
            if(destHeight<=0) destHeight = 150; // 切片高度
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > destWidth && srcHeight > destHeight) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                int cols = 0; // 切片横向数量
                int rows = 0; // 切片纵向数量
                // 计算切片的横向和纵向数量
                if (srcWidth % destWidth == 0) {
                    cols = srcWidth / destWidth;
                } else {
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;
                }
                if (srcHeight % destHeight == 0) {
                    rows = srcHeight / destHeight;
                } else {
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
                                destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                new FilteredImageSource(image.getSource(),
                                        cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth,
                                destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(descDir
                                + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     * @param srcImageFile 源图像地址
     * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile 目标图像地址
     */
    public final static void convert(String srcImageFile, String formatName, String destImageFile) {
        try {
            File f = new File(srcImageFile);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 彩色转为黑白 
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     */
    public final static void gray(String srcImageFile, String destImageFile) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(destImageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String pressText,
            String srcImageFile, String destImageFile, String fontName,
            int fontStyle, Color color, int fontSize,int x,
            int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName 字体名称
     * @param fontStyle 字体样式
     * @param color 字体颜色
     * @param fontSize 字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText2(String pressText, String srcImageFile,String destImageFile,
            String fontName, int fontStyle, Color color, int fontSize, int x,
            int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 给图片添加图片水印
     * @param pressImg 水印图片
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param x 修正值。 默认在中间
     * @param y 修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(String pressImg, String srcImageFile,String destImageFile,
            int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text
     * @return
     */
    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }
    
    
    /**
	 * 保存截图。
	 * @param bufImg
	 * @param imgType
	 * @param tarPath
	 */
	public static String save(String imgType,String imgName,String tarPath,int width,int height,BufferedImage selfImg){
		
		BufferedImage img = selfImg == null ? subImg : selfImg;
		
		if(!new File(tarPath).exists())
				new File(tarPath).mkdirs();
		
		String path = tarPath+imgName;
		LOG.debug("上传图片路径：", path);
		try {/**压缩图片为指定尺寸*/
			if(img.getWidth()!=width || img.getHeight()!=height){
				BufferedImage tempImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
				tempImg.getGraphics().drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,null);
				
				ImageIO.write(tempImg, imgType, new File(path));
			}else{
				ImageIO.write(img,imgType,new File(path));
			}
		} catch (IOException e) {
			System.out.print("上传图片出现异常，图片名称："+imgName+"；异常信息："+e.getMessage());
			e.printStackTrace();
			
		}
		return getUrl(path);
	}
	
	/**
	 * 根据本地路径计算网络路径
	 * @param path
	 * @return
	 */
	public static String getUrl(String path){
		
		if(path == null /*|| path.indexOf("/images/")<0*/){
			return null;
		}
//		path = path.substring(path.lastIndexOf("/images/"));
		//path = WYFConfig.getProperty("path")  + path ;
		return path;
		
	}
	
	
	
	/** 
	  * 根据缩放后图片的宽和高计算缩放的比例 
	  * @version 2011-6-1 
	  * @param source  图片的原始存放路径 
	  * @param w  缩放后的图片的宽度 
	  * @param h  缩放后的图片的高度 
	  * @return 返回缩放后的图片的缓存 
	  */  
	public static BufferedImage equalScale(String source, int w, int h){
	      if(source == null || "".equals(source.trim())){  
	          return null;  
	      }  
	      try {  
	          BufferedImage buffer = ImageIO.read(new File(source));  
	          int sh = buffer.getHeight(null);  
	          int sw = buffer.getWidth(null);  
	          if(sh <= h && sw <= w){  
	              return buffer;  
	          }
	          if(w==0 && h==0){//返回原始图片
	        	  return buffer;
	          }
	          int finalW = 0;  
	          int finalH = 0;  
	          if(sh > h && sw < w && h>0){//此时高必须大于0,如果高等于0，缩放没意义
	              double scale = (double)h / (double) sh;  
	              finalW = (int)(sw * scale);  
	              finalH = h;  
	          }else if(sh < h && sw > w && w>0){//此时宽要大于0,如果宽等于0，缩放没意义
	              finalW = w;  
	              double scale = (double)h / (double) sh;  
	              finalH = (int)(sh * scale);  
	          }else if(sh > h && sw > w && h>0 && w>0){  //此时宽和高要大于0，,如果等于0，缩放没意义
	              double scale = (double)h / (double) sh;  
	              if(sh > sw){  
	                  scale = (double)h / (double) sh;  
	              }else if(sw > sh){  
	                  scale = (double)w / (double) sw;  
	              }  
	              finalH = (int)(sh * scale);  
	              finalW = (int)(sw * scale);  
	          }else{//如果传入宽和高，要么同时为0，要不都不为0
	        	  return buffer;
	          }  
	          return zoomImage(buffer, finalW, finalH);  
	      } catch (IOException e) {  
	          e.printStackTrace();  
	      }  
	      return null;  
	  }  

	  /*** 
	   * 缩放图像 
	   * @param buffer 图片缓存 
	   * @param width  缩放后的宽度 
	   * @param height 缩放后的高度 
	   * @return 返回缩放后的 图片缓存 
	   */  
	  public static BufferedImage zoomImage(BufferedImage buffer, int width, int height){  
	      try {  
	          if(buffer == null){  
	              return buffer;  
	          }  
	           AreaAveragingScaleFilter areaAveragingScaleFilter = new AreaAveragingScaleFilter(    
	                      width, height);    
	              FilteredImageSource filteredImageSource = new FilteredImageSource(buffer    
	                      .getSource(), areaAveragingScaleFilter);  
	          BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	          Graphics g =  result.getGraphics();  
	          Canvas canvas = new Canvas();  
	          g.drawImage(canvas.createImage(filteredImageSource), 0, 0, null);  
	          return result;  
	      } catch (Exception e) {  
	          e.printStackTrace();  
	      }  
	      return null;  
	  } 
	  
	  
	  /**
	   * 将图片进行Base64位编码
	   * @param bufferedImage
	   * @return base64
	   */
	  public static String encodeImgageToBase64(BufferedImage bufferedImage) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	    ByteArrayOutputStream outputStream = null;
	    try {
	      outputStream = new ByteArrayOutputStream();
	      ImageIO.write(bufferedImage, "jpg", outputStream);
	    } catch (MalformedURLException e1) {
	    	e1.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    // 对字节数组Base64编码
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
	  }
	  
	  
	  /**
		 * 获取4位数字验证码 && 存放到Shiro 的 Session里
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		public static void getYZM(HttpServletResponse response,String type) throws IOException{
			//设置页面不缓存
			response.setHeader("Pragma","No-cache");
			response.setHeader("Cache-Control","no-cache");
			response.setDateHeader("Expires", 0);
			// 在内存中创建图象
			int width=130, height=40;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics2D g = image.createGraphics(); 
			//生成随机类
			Random random = new Random();
			// 设定背景色
			g.setColor(getRandColor(200,250));
			g.fillRect(0, 0, width, height);
			//设定字体
			g.setFont(new Font("Fixedsys",Font.PLAIN,38));
			//画边框
			//g.setColor(new Color());
			//g.drawRect(0,0,width-1,height-1);
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160,200));
			for (int i=0;i<255;i++){
			 int x = random.nextInt(width);
			 int y = random.nextInt(height);
			        int xl = random.nextInt(12);
			        int yl = random.nextInt(12);
			 g.drawLine(x,y,x+xl,y+yl);
			}
			
			if("number".equals(type)){
				
				// 取随机产生的认证码(4位数字)
				String sRand="";
				for (int i=0;i<4;i++){
				    String rand=String.valueOf(random.nextInt(10));
				    sRand+=rand;
				    
				    // 将认证码显示到图象中
				    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
				//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
					rand += "  ";
				    g.drawString(rand,30*i+16,36);
				}
				LOG.debug("生成的字符串：",sRand);
			
				
			}else if("numeng".equals(type)){
				//生成字母加数字
				 String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",  
			                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
			                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
			                "W", "X", "Y", "Z" };  
			        List list = Arrays.asList(beforeShuffle);  
			        Collections.shuffle(list);  
			        StringBuilder sb = new StringBuilder();  
			        for (int i = 0; i < list.size(); i++) {  
			            sb.append(list.get(i)+"@");  
			        }  
			        String afterShuffle = sb.toString();  
			        String result = afterShuffle.substring(5, 13);  
			        
			        String drowstring = "";
			        if(result!=null && result.length()==8){
			        	
			        	String[] codes = result.split("@");
			        	LOG.debug("生成字符长度：",codes.length);
			        	
			        	for(int i=0;i<codes.length;i++){
			        		String rand=codes[i];
			        		drowstring+=rand;
			        		 // 将认证码显示到图象中
						    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
			        		//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			        		rand += "  ";
			        		g.drawString(rand,20*i+8,36);
			        	}
			        }
			        LOG.debug("生成的字符串：", drowstring);
			        
			}else if("english".equals(type)){
				
				//生成字母加数字
				 String[] beforeShuffle = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
			                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
			                "W", "X", "Y", "Z" };  
			        List list = Arrays.asList(beforeShuffle);  
			        Collections.shuffle(list);  
			        StringBuilder sb = new StringBuilder();  
			        for (int i = 0; i < list.size(); i++) {  
			            sb.append(list.get(i)+"@");  
			        }  
			        String afterShuffle = sb.toString();  
			        String result = afterShuffle.substring(5, 13);  
			        
			        String drowstring = "";
			        if(result!=null && result.length()==8){
			        	String[] codes = result.split("@");
			        	for(int i=0;i<codes.length;i++){
			        		String rand=codes[i];
			        		drowstring+=rand;
			        		 // 将认证码显示到图象中
						    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
			        		//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			        		rand += "  ";
			        		g.drawString(rand,20*i+8,36);
			        	}
			        }
			        LOG.debug("生成的字符串：",drowstring);
			}
			// 图象生效
			g.dispose();
			// 输出图象到页面
			ImageIO.write(image, "JPEG", response.getOutputStream());
			//out.clear();
			//out = pageContext.pushBody();
		}
		
		private static Color getRandColor(int fc,int bc){//给定范围获得随机颜色
	        Random random = new Random();
	        if(fc>255) fc=255;
	        if(bc>255) bc=255;
	        int r=fc+random.nextInt(bc-fc);
	        int g=fc+random.nextInt(bc-fc);
	        int b=fc+random.nextInt(bc-fc);
	        return new Color(r,g,b);
	    }
	  

		/**
		 * 传入图片名称判断图片格式
		 */
		public static boolean isUploadImage(String fileName) {
			// 默认是返回false
			boolean flag = Boolean.FALSE;
			if (StringUtils.isBlank(fileName)) {
				return flag;
			}
			// 调用返回后缀.png|.gif|.jpg类型，与定义jpg|png，需要去掉.
			String suffix = Tools.getFileSuffix(fileName);
			if (ArrayUtils.indexOf(IMAGE_UPLOAD_TYPE,
					StringUtils.remove(suffix, ".").toLowerCase()) != -1) {
				flag = true;
			}
			return flag;
		} 
}
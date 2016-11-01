package com.yogapay.boss.utils;

import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
public class ResizeImage {
 
    /**
     * @param im            原始图像
     * @param resizeTimes   需要缩小的倍数，缩小2倍为原来的1/2 ，这个数值越大，返回的图片越小
     * @return              返回处理后的图像
     */
    public BufferedImage resizeImage(BufferedImage im, float resizeTimes) {
        /*原始图像的宽度和高度*/
        int width = im.getWidth();
        int height = im.getHeight();
 
        /*调整后的图片的宽度和高度*/
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / resizeTimes);
 
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
 
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
 
    /**
     * @param im            原始图像
     * @param resizeTimes   倍数,比如0.5就是缩小一半,0.98等等double类型
     * @return              返回处理后的图像
     */
    public BufferedImage zoomImage(BufferedImage im, float resizeTimes) {
        /*原始图像的宽度和高度*/
        int width = im.getWidth();
        int height = im.getHeight();
 
        /*调整后的图片的宽度和高度*/
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);
 
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
 
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
    
    
    /**
     * @param im            原始图像
     * @param resizeTimes   倍数,比如0.5就是缩小一半,0.98等等double类型
     * @return              返回处理后的图像
     * @throws IOException 
     */
    public void zoomImage(String filePath,String savePath, float resizeTimes) throws IOException {
    	
    	BufferedImage im = javax.imageio.ImageIO.read(new File(filePath)) ;
        /*原始图像的宽度和高度*/
        int width = im.getWidth();
        int height = im.getHeight();
 
        /*调整后的图片的宽度和高度*/
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);
 
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
          
        FileOutputStream newimage = new FileOutputStream(savePath);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(result);
           /* 压缩质量 */
           jep.setQuality(1f, true);
           encoder.encode(result, jep);
          /*近JPEG编码*/
           newimage.close();
    }
    
    /**
     * @param im            原始图像
     * @param resizeTimes   倍数,比如0.5就是缩小一半,0.98等等double类型
     * @return              返回处理后的图像
     * @throws IOException 
     */
    public void zoomImage(String filePath, float resizeTimes) throws IOException {
    	
    	BufferedImage im = javax.imageio.ImageIO.read(new File(filePath)) ;
        /*原始图像的宽度和高度*/
        int width = im.getWidth();
        int height = im.getHeight();
 
        /*调整后的图片的宽度和高度*/
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);
 
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
          
        FileOutputStream newimage = new FileOutputStream(filePath);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(result);
           /* 压缩质量 */
           jep.setQuality(1f, true);
           encoder.encode(result, jep);
          /*近JPEG编码*/
           newimage.close();
    }
    
    /**
     * @param im            原始图像
     * @param toWidth       宽度
     * @param toHeight      高度
     * @return              返回处理后的图像
     */
    public BufferedImage zoomImage(BufferedImage im, int toWidth, int toHeight) {
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
    
    
    /**
     * 返回文件的文件后缀名
      * @param fileName
      * @return
    */
    public String getExtension(String fileName) {
        try {
            return fileName.split("\\.")[fileName.split("\\.").length - 1];
        } catch (Exception e) {
            return null;
        }
    }
 
    /**
     *      
     * @param path    原图路径
     * @param zoom    是否压缩
     * @param toWidth     转换后高度
     * @param toHeight    转换后宽度
     * @return
     * @throws IOException 
     */
    public FileInputStream getImageFileInputStream(String path,String fileName,boolean zoom,int toWidth,int toHeight) throws IOException {
    	String newPath = path ;
    	File file = new File(newPath) ;
         if (zoom) {
           /*输出到文件流*/
      	  newPath = ConstantsLoader.getProperty("file_root").concat("/images/temp/"+fileName) ;
      	  file = new File(newPath) ;
      	  if (!file.exists()) {
      		  BufferedImage im = javax.imageio.ImageIO.read(new File(path)) ;
      		  im = zoomImage(im, toWidth, toHeight) ;
              FileOutputStream newimage = new FileOutputStream(newPath);
              JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
              JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
              /* 压缩质量 */
              jep.setQuality(1f, true);
              encoder.encode(im, jep);
             /*近JPEG编码*/
              newimage.close();
		   }
		}
        return new FileInputStream(file) ;
	}
    
    
  

 

 
    public static void main(String[] args) throws Exception{
 
        ResizeImage r = new ResizeImage();
        //r.getImageFileInputStream("D:\\boss\\file\\banner\\1415317046782.png","1415317046782.png", true, 300, 300) ;
        r.zoomImage("D:\\boss\\file\\banner\\1111.png","D:\\boss\\file\\banner\\1111.png", Float.valueOf("0.3")) ;
        
        
    }
}
package com.yogapay.boss.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCompress {
	public static void ImageScale(File file, String path, int maxWidth) {
		try {
			Image image = javax.imageio.ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			float ratio = getRatio(width, maxWidth);
			if (ratio == 1.0) {
				ImageScale(file, path);
			} else {
				width = (int) (width * ratio);
				height = (int) (height * ratio);
				image = image.getScaledInstance(width, height,
						Image.SCALE_AREA_AVERAGING);
				BufferedImage mBufferedImage = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g2 = mBufferedImage.createGraphics();
				g2.drawImage(image, 0, 0, width, height, Color.white, null);
				g2.dispose();
				float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
						-0.125f, -0.125f, -0.125f, -0.125f };
				Kernel kernel = new Kernel(3, 3, kernelData2);
				ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
						null);
				mBufferedImage = cOp.filter(mBufferedImage, null);
				FileOutputStream out = new FileOutputStream(path);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(mBufferedImage);
				out.close();
			}
		} catch (FileNotFoundException fnf) {
		} catch (IOException ioe) {
		} finally {
		}
	}

	public static void ImageScale(File file, String path, int width,int height) {
		try {
			Image image = javax.imageio.ImageIO.read(file);
				image = image.getScaledInstance(width, height,
						Image.SCALE_AREA_AVERAGING);
				BufferedImage mBufferedImage = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g2 = mBufferedImage.createGraphics();
				g2.drawImage(image, 0, 0, width, height, Color.white, null);
				g2.dispose();
				float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
						-0.125f, -0.125f, -0.125f, -0.125f };
				Kernel kernel = new Kernel(3, 3, kernelData2);
				ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
						null);
				mBufferedImage = cOp.filter(mBufferedImage, null);
				FileOutputStream out = new FileOutputStream(path);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(mBufferedImage);
				out.close();
		} catch (FileNotFoundException fnf) {
		} catch (IOException ioe) {
		} finally {
		}
	}
	
	public static void ImageScale(File file, String path) {
		File target = new File(path);
		try {
			FileUtils.copyFile(file, target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ImageScale(File file, String dir,String filename) {
		File target = new File(dir,filename);
		try {
			FileUtils.copyFile(file, target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static float getRatio(int width, int maxWidth) {
		return maxWidth > width ? 1 : ((float) maxWidth / width);
	}
}

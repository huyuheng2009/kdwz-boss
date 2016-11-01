/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class FileCommonUtil {

	/**
	 * 拷贝二进制文件
	 *
	 * @param inPath 输入文件路径
	 * @param outPath 输出文件路径
	 * @throws IOException
	 */
	public static void copyFile(String inPath, String outPath)
			throws IOException {
		System.out.println("复制文件-原路径:" + inPath);
		System.out.println("复制文件-目标路径:" + outPath);

		File inFile = new File(inPath);
		if (!inFile.exists()) {
			return;
		}
		System.out.println(inFile.getName());
		FileInputStream fis = new FileInputStream(inFile);

		//String filePath = outPath.substring(0, outPath.lastIndexOf("/"));
		File fp = new File(outPath);
		if (!fp.exists()) {
			fp.mkdirs();
		}
		//
		FileOutputStream fos = new FileOutputStream(new File(outPath + "/" + inFile.getName()));

		byte[] buffer = new byte[1024];
		int c;
		while ((c = fis.read(buffer)) != -1) {
			fos.write(buffer, 0, c);
		}
		fis.close();
		fos.close();
		inFile.delete();
	}
}

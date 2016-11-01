/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.controller;

import com.yogapay.boss.domain.JSONResult;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.utils.DateUtil;
import com.yogapay.boss.utils.FileCommonUtil;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.UnZipUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 面单上传
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/plane")
public class SinglePlaneController extends BaseController {

	@Autowired(required = false)
	private ServletContext servletContext;
	@Resource(name = "config")
	private Properties properties;
	@Value("#{config['plane_dir']}")
	private String devDir;
	@Value("#{config['dir_root']}")
	private String dir;
	@Autowired
	private OrderService orderService;

	@RequestMapping("uploadPage")
	public Object upload_page() {
		return "plane/uploadPlane";
	}

	@RequestMapping(value = "/uploadPlance", headers = "Content-Type=multipart/form-data")
	public void upload_plance(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		try {
			//properties.getProperty("devDir")
			String path = "";
			String dateDir = DateUtil.getDayCurrent();
			String filePath = dir.substring(dir.lastIndexOf("/"), dir.length()) + devDir + "/" + dateDir + "/";
			if (Boolean.parseBoolean(properties.getProperty("dev"))) {
				path = servletContext.getRealPath(devDir + "/" + dateDir);
			} else {
				path = dir + devDir + "/" + dateDir;
			}
//            File f = new File(path);
//            if(!f.exists()){
//               f.mkdirs();
//            }
			String fileName = file.getOriginalFilename();
			String dirFileName = fileName.substring(0, fileName.indexOf("."));
			String backDir = devDir + "/" + dateDir + "/" + dirFileName;
//        String fileName = new Date().getTime()+".jpg";
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			file.transferTo(targetFile);
			List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
			List<String> list = UnZipUtil.unZip(new File(path + "/" + fileName), path);
			for (String str : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("lgcOrderNo", str.substring(str.lastIndexOf("/") + 1, str.lastIndexOf(".")));
				map.put("imgName", str.substring(str.lastIndexOf("/") + 1, str.length()));
				map.put("imgPath", devDir + "/" + dateDir + "/" + str);
				mList.add(map);
			}
			JSONResult result = new JSONResult();
			result.setErrorCode(0);
			result.setMessage(backDir);
			//result.setMessage("上传成功,可以点击图片浏览查看图片列表");
			//result.setValue(backDir);
			result.setValue(mList);
			outJson(JsonUtil.toJson(result), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/upload_img", headers = "Content-Type=multipart/form-data")
	public void upload_plance(@RequestParam("file") MultipartFile[] file, String type, HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = "";
			String dateDir = DateUtil.getDayCurrent();
			String filePath = "";
			if (Boolean.parseBoolean(properties.getProperty("dev"))) {
				path = servletContext.getRealPath(devDir + "/" + dateDir);
				filePath = devDir + "/" + dateDir + "/";
			} else {
				path = dir + devDir + "/" + dateDir;
				filePath = dir.substring(dir.lastIndexOf("/"), dir.length()) + devDir + "/" + dateDir + "/";
			}
			List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
			StringBuilder strbuf = new StringBuilder();
			if (file != null && file.length > 0) {
				//循环获取file数组中得文件
				for (int i = 0; i < file.length; i++) {
					MultipartFile filex = file[i];
					//保存文件
					String fileName = filex.getOriginalFilename();
					String dirFileName = fileName.substring(0, fileName.indexOf("."));
					Map<String, Object> rp = this.orderService.selectOrderExit(dirFileName);
					if (rp != null && rp.size() > 0 && "0".equals(rp.get("count").toString())) {
						strbuf.append(dirFileName).append(",");
					} else {
						//String backDir = devDir + "/" + dirFileName;
//        String fileName = new Date().getTime()+".jpg";
						File targetFile = new File(path, fileName);
						if (!targetFile.exists()) {
							targetFile.mkdirs();
						}
						filex.transferTo(targetFile);
						Map<String, String> map = new HashMap<String, String>();
						map.put("lgcOrderNo", dirFileName);
						map.put("imgName", fileName);
						map.put("imgPath", filePath + fileName);
						mList.add(map);
					}
				}
			}
			if (mList.size() > 0) {
				this.orderService.updateImage(mList, type);
			}
			JSONResult result = new JSONResult();
			result.setErrorCode(0);
			//result.setMessage(backDir);
			if (StringUtil.isEmptyString(strbuf.toString())) {
				result.setMessage("上传成功");
			} else {
				result.setMessage("运单号：" + strbuf.substring(0, strbuf.length() - 1) + "不存在");
			}
			//result.setValue(backDir);
			result.setValue(mList);
			outJson(JsonUtil.toJson(result), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "queryFileText")
	public Object queryFileText(String filePath, HttpServletRequest request) {
		File file = new File(servletContext.getRealPath(filePath));
		File flist[] = file.listFiles();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String dir = filePath + "/";
		for (File f : flist) {
			//这里将列出所有的文件
			if (f.isFile()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("lgcOrderNo", f.getName().substring(0, f.getName().indexOf(".")));
				map.put("imgName", f.getName());
				map.put("imgPath", dir + f.getName());
				list.add(map);
			}
		}
		request.getSession().setAttribute("imList", list);
		return "";
	}

	@RequestMapping(value = "updateCopyFile")
	public Object updateCopyFile(String filePaths, String typeId, HttpServletRequest request) throws IOException {
//		if (!StringUtils.isEmpty(filePaths)) {
//			String[] imageArray = filePaths.split(",");
//			for (String imgPath : imageArray) {
//				FileCommonUtil.copyFile(servletContext.getRealPath(imgPath), dir);
//			}
//		}
		List<Map<String, String>> list = (List<Map<String, String>>) request.getSession().getAttribute("imList");
		for (Map<String, String> mp : list) {
			FileCommonUtil.copyFile(servletContext.getRealPath(mp.get("imgPath")), "F:/d/e");
		}
		return "";
	}
}

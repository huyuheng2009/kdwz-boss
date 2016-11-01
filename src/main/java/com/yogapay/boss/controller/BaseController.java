package com.yogapay.boss.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.exception.FileUnknowTypeException;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.RequestFile;
import com.yogapay.boss.utils.StringUtils;

/**
 * 提供获取request,session,转换json等常用方法
 * 
 * @author dj
 * 
 */
public class BaseController {

	// header 常量定义
	private static final String DEFAULT_ENCODING = "GBK";
	private static final boolean DEFAULT_NOCACHE = true;
	// Content Type 常量定义
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";
	public static final String  PARRENT ="yyyy-MM-dd";
	public static int PAGE_NUMERIC = 20;
	//public Page<Map<String, Object>> pageList = null;
	@Resource
	public UserService userService ;
	@Autowired
	public ConfigInfo configInfo ;
	
	@Resource
	 public DynamicDataSourceHolder dynamicDataSourceHolder ;
	
	public PageInfo<Map<String, Object>> pageList = null;

	public PageRequest getPageRequest(int cpage) {
		return new PageRequest(cpage - 1, PAGE_NUMERIC);
	}
	
	public PageRequest getPageRequest(int cpage,int size) {
		if(size==0) size = PAGE_NUMERIC;
		return new PageRequest(cpage - 1, size);
	}
	
	public Page getPageInfo(int cpage) {
		return new Page(cpage, PAGE_NUMERIC);
	}
	
	 public Page getPageInfo(int cpage,int size) {
		if(size==0) size = PAGE_NUMERIC;
		return new Page(cpage, size);
	}

	/**
	 * 直接输出内容的简便函数.
	 */
	protected void render(final String contentType, final String content,
			final HttpServletResponse response) {
		HttpServletResponse resp = initResponseHeader(contentType, response);
		try {
			resp.getWriter().write(content);
			resp.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 直接输出文本.
	 */
	protected void outText(final String text, final HttpServletResponse response) {
		render(TEXT_TYPE, text, response);
	}

	/**
	 * 直接输出HTML.
	 */
	protected void outHtml(final String html, final HttpServletResponse response) {
		render(HTML_TYPE, html, response);
	}

	/**
	 * 直接输出XML.
	 */
	protected void outXml(final String xml, final HttpServletResponse response) {
		render(XML_TYPE, xml, response);
	}

	/**
	 * 输出JSON,可以接收参数如： [{'name':'www'},{'name':'www'}],['a','b'],new
	 * String[]{'a','b'},合并如下：jsonString = "{TOTALCOUNT:" + totalCount + ",
	 * ROOT:" + jsonString + "}";
	 * 
	 * @param jsonString
	 *            json字符串.
	 * 
	 */
	protected void outJson(final String json, final HttpServletResponse response) {
		render(JSON_TYPE, json, response);
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	protected void setFileDownloadHeader(HttpServletResponse response,
			String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = new String(fileName.getBytes(),
					"ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ encodedfileName + "\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分析并设置contentType与headers.
	 */
	protected HttpServletResponse initResponseHeader(final String contentType,
			final HttpServletResponse response) {
		// 分析headers参数
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		// 设置headers参数
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			setNoCacheHeader(response);
		}

		return response;
	}


	/**
	 * 设置客户端无缓存Header.
	 */
	protected void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}
	
	/**
	 * 
	 * @param model
	 * @param json  是否json格式
	 * @return
	 */
	public ModelMap setModelSub(ModelMap model,String json,HttpServletRequest request) {
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
		if ("Y".equals(json)) {
			model.put("slist", JsonUtil.toJson(substations));
		}else {
			model.put("substations", substations);
		}
		
		return model ;
	}
	
	
	/**
	 * 
	 * @param model
	 * @param json  是否json格式
	 * @return
	 */
	public ModelMap setModelCour(ModelMap model,String json,CourierService courierService,HttpServletRequest request) {
		Map<String, String> pMap = new HashMap<String, String>() ;
		pMap = setSubstationNo(pMap,request) ;
		PageInfo<Map<String, Object>> courierList = courierService.list(pMap,getPageInfo(1,5000));
		if ("Y".equals(json)) {
			model.put("clist", JsonUtil.toJson(courierList.getList()));
		}else {
			model.put("clist", courierList.getList());
		}
		return model ;
	}
	
	public Map<String, String> setSubstationNo(Map<String, String> params,HttpServletRequest request) {
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		params.put("uuu", bossUser.getUserName()) ;
		String substationNo ;
		System.out.println(!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION"));
		System.out.println("1".equals(StringUtils.nullString(request.getAttribute("sub_limit"))));
		System.out.println(StringUtils.nullString(request.getAttribute("sub_limit")));
		if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit"),"1"))) {
			 params.put("slimit", "0");   //是否需要分站控制
			 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
		}else {
			 params.put("slimit", "1");
			substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
		}
		if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			
			params.put("substationNo", substationNo);
		}else {
			 params.put("slimit", "1");
			 String s = params.get("substationNo") ;
			 if (StringUtils.nullString(s).contains("(")) {
					s = s.substring(0, s.indexOf("("));
				}
			if (!substationNo.contains(s)) {
				params.put("substationNo", "0000") ;
			}else {
				params.put("substationNo", s) ;
			}
		}
		return params ;
	}
	
	/**
	 * 
	 * @param request   
	 * @param fileName   限定文件的字段名称
	 * @param rootPath   文件保存的根目录
	 * @param contextPath  文件保存的目录
	 * @param mime  限定文件的mime类型集合
	 * @return
	 * @throws Exception
	 */
	protected List<RequestFile> getFile(HttpServletRequest request,String fileName,String rootPath,String contextPath,List<String> mime) throws Exception{
		
		 RequestFile requestFile = null ;
		 List<RequestFile> requestFiles = new ArrayList<RequestFile>() ;
		 MultipartRequest multipartRequest ;
		 try {
		  multipartRequest = (MultipartRequest) request ;
		} catch (Exception e) {
		//	e.printStackTrace();
          return requestFiles ;
		}
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
     			{
     				if (StringUtils.isEmptyWithTrim(fileName)) {
     					for(String name:fileMap.keySet()){
     						if (fileMap.get(name).isEmpty()) {
								continue ;
							}else {
								boolean flag = false ;
								for (String type:mime) {
									if (type.equals(fileMap.get(name).getContentType())) {
										System.out.println(fileMap.get(name).getContentType()+"==========================================");
										flag = true ;
										break ;
									}
								}
								if (mime==null||mime.size()<=0) {
									flag = true ;
								}
								if (!flag) {
									throw new FileUnknowTypeException() ;
								}
     								requestFile = new RequestFile() ;
     								requestFile.setName(name);
     		     					String prefix = fileMap.get(name).getOriginalFilename().substring(fileMap.get(name).getOriginalFilename()
     		     							.indexOf("."));
     		     					requestFile.setFileName(System.currentTimeMillis()+StringUtils.getRandomString(6, true)+prefix);
     		     					requestFile.setOrginalName(fileMap.get(name).getOriginalFilename());
     		     					requestFile.setFileSize(fileMap.get(name).getSize()+"");
     		     					requestFile.setFileType(fileMap.get(name).getContentType());
     		     					requestFile.setFilePath(contextPath.concat("/").concat(requestFile.getFileName()));
     		     					File dir = new File(rootPath.concat("/").concat(contextPath));
     		     					if(!dir.isDirectory()){
     		     						dir.mkdirs();
     		     					}
     								FileCopyUtils.copy(fileMap.get(name).getBytes(),new File(rootPath.concat("/").concat(requestFile.getFilePath())));
     								requestFiles.add(requestFile) ;
							    }	
     	     				}//for
					}else {
						for(String name:fileMap.keySet()){
		     				  if (!name.equals(fileName)||fileMap.get(name).isEmpty()) {
									continue ;
								}else {
									boolean flag = false ;
									for (String type:mime) {
										if (type.equals(fileMap.get(name).getContentType())) {
											System.out.println(fileMap.get(name).getContentType()+"222222222222222222222222222222222");
											flag = true ;
											break ;
										}
									}
									if (mime==null||mime.size()<=0) {
										flag = true ;
									}
									if (!flag) {
										throw new FileUnknowTypeException() ;
									}
									requestFile = new RequestFile() ;
									requestFile.setName(name);
			     					String prefix = fileMap.get(name).getOriginalFilename().substring(fileMap.get(name).getOriginalFilename()
			     							.indexOf("."));
			     					requestFile.setFileName(System.currentTimeMillis()+prefix);
			     					requestFile.setOrginalName(fileMap.get(name).getOriginalFilename());
			     					requestFile.setFileSize(fileMap.get(name).getSize()+"");
			     					requestFile.setFileType(fileMap.get(name).getContentType());
			     					requestFile.setFilePath(contextPath.concat("/").concat(requestFile.getFileName()));
     		     					File dir = new File(rootPath.concat(contextPath));
			     					if(!dir.isDirectory()){
			     						dir.mkdirs();
			     					}
			     					FileCopyUtils.copy(fileMap.get(name).getBytes(),new File(rootPath.concat("/").concat(requestFile.getFilePath())));
									requestFiles.add(requestFile) ;
									break ;
								}
		     				}//for
					}
     		
     			}
		return requestFiles ;
	}
	
}

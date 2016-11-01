<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript">
$(function(){
	
	 
	 
	 $('.download_type').click(function(){
		 if($('input[name="download_type"]:checked').val()=="web"){
			 
			$('.web_type').css("display","block"); 
			$('.upload_type').css("display","none"); 
		 }else{
			$('.web_type').css("display","none"); 
			$('.upload_type').css("display","block"); 
		 }
		 
		 
	});
	 
	 
}); 

function check(){
	 var api = frameElement.api, W = api.opener;
	 var status = $('select[name=status]').val();
	 if(status==''){
		 alert('请选择状态');
		 return false;
	 }
	 
	 if($('input[name=version]').val().length<2){
		 alert('请输入应用版本');
		 return false;
	 }
	 if($('input[name=downloadAddress]').val().length<2){
		 alert('请输入下载地址');
		 return false;
	 }
	 
	   if($('input[name="download_type"]:checked').val()=="web"){
       	 if($('input[name=address]').val().length<1){
   			 alert('请输入下载地址');
   			 return false;
   		 }
        }else{
        	if($('input[name=uri_fit]').val().length<1){
   			 alert('请输入前缀地址');
   			 return false;
   		 }
       	 if($('input[name=installFile]').val()==''){
   			 alert('请选择文件');
   			 return false;
   		 }
        }
};
 
</script>

</head>
<body>
	<div class="content">
		<form class="tbdata" action="vsave"  enctype="multipart/form-data" method="post" onsubmit="return check()">
		 	<div class="item">
		 	    <c:if test="${!empty appVersion.id}"><input  type="hidden" id="id" name="id" value="${appVersion.id}"/></c:if>
	 			<ul  style="padding-top:20px;padding-left: 20px;">
	 				<li style="width:420px">应用名称：
		 				<select id="appCode" name="appCode" style="width: 350px">
							<c:forEach items="${appProductList}" var="item" varStatus="status">
								<option value="${item.appCode }" <c:out value="${appVersion.appCode eq item.appCode?'selected':'' }"/> >${item.appName }</option>
							</c:forEach>
					  </select>
		 			</li>
		 			 <li style="width:220px">应用平台：
		 			        <select name="platform" style="width: 140px">
                               <option value="android" ${appVersion.platform eq 'android'?'selected':''}>安卓</option>
                               <option value="ios" ${appVersion.platform eq 'ios'?'selected':''}>苹果</option>
                             </select>
                     </li>
		 			<li style="width:420px">启用状态：
		 			 <u:select sname="status" stype="SWITCH" value="${appVersion.status }"  style="width:140px"/>
		 			<li style="width:260px">应用版本：
		 				<input maxlength="20"   type="text" name="version" value="${appVersion.version }" ></input>
		 			</li>
		 			<li style="width:150px">
		 				<input type="checkbox" name="mupdate" value="1"  style="width: 30px"  <c:out value="${appVersion.mupdate==1?'checked':'' }"/>></input>是否要求强制更新
		 			</li>
		 			
		 			<li style="width:420px;display: none;">下载地址：
		 			<input type="text" value="downloadAddress" name="${appVersion.downloadAddress }" style="width: 320px"></input></li>
		 			
		 			<li style="width:420px">安装包地址：
		 			<input type="radio" name="download_type" class="download_type" checked="checked" value="web" style="width:20px;">文本链接</input>
		 			&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" class="download_type" name="download_type" value="upload"  style="width:20px;">上传</input></li>
		 			
		 			
		 			<li style="width:420px" class="web_type">文件地址：
		 			<input type="text" value="${appVersion.downloadAddress }" name="address" style="width: 320px"></input></li>
		 			
		 			<li  style="width:420px;display: none" class="upload_type">前缀地址：
		 			<input  type="text" name="uri_fit"  style="width: 320px"></input></li>
		 			<li  style="width:420px;display: none" class="upload_type">上传文件：
		 			 <input type="file" name="installFile" value="上传文件"/></li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" type="submit"  value="保存" />
	 		</form>
	 	</div>
	</div>
</body>

</html>
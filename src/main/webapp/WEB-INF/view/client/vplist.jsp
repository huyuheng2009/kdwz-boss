<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript">
$(function(){
	


	 
	 
}); 

function check(){
	 var api = frameElement.api, W = api.opener;
       	 
	 if($('input[name=uri_fit]').val().length<1){
			 alert('请输入前缀地址');
			 return false;
		 }
	 if($('input[name=installFile]').val()==''){
   			 alert('请选择文件');
   			 return false;
   		 }

};
 
</script>

</head>
<body>
	<div class="content">
		<form class="tbdata" action="plistsave"  enctype="multipart/form-data" method="post" onsubmit="return check()">
		 	<div class="operator"  style="padding-top: 8px"></div>
		 	<div class="item">
		 	   <input  type="hidden" id="id" name="id" value="${params.id}"/>
		 	   <ul>
		 	   <li  style="width:420px;">前缀地址：
		 			<input class="ltdlen_200" type="text" name="uri_fit"  style="width: 320px"></input></li>
	 		   <li style="width:420px;" >上传文件：
		 			 <input type="file" name="installFile" value="上传文件"/></li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px"></div>
	 		<input class="button input_text  big blue_flat" type="submit"  value="保存" />
	 		</form>
	 	</div>
</body>

</html>
<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript">
$(function(){
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 category = $('select[name=category]').val();
		 if(category==''){
			 alert('请选择权限类别');
			 return false;
		 }
		 parentId = $('select[name=parentId]').val();
		  
		 authName = $('input[name=authName]').val();
		 if(authName.length<2){
			 alert('请输入权限名称');
			 return false;
		 }
		 authCode = $('input[name=authCode]').val();
		 if(authCode.length<2){
			 alert('请输入权限代码');
			 return false;
		 }
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/system/asave",//要访问的后台地址
	            data: {'category':category,'authName':authName,'authCode':authCode,'parentId':parentId},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功');
	            		api.reload();
						api.close();
	            	}else{
	            		alert(msg);
	            	}
	            }
		 });
	 });
	
}); 
 
</script>
</head>
<body>
	 <div class="content">
	 <form action="${ctx }/system/asave" method="post">
	 	<div class="item">
	 		 <input  type="hidden" id="id" name="id" value="${params.id }"/>
	 		<ul style="padding-top:20px">
	 			<li style="width:220px">上级权限：
	 			<select id="parentId"  name="parentId" style="width: 137px"  class="required" >
	        	<c:forEach items="${authList}" var="authItem" varStatus="status">
	         		<option value="${authItem.id }">${authItem.auth_name }</option>
	         	</c:forEach>
	         </select>
	 			</li>
	 			<li style="width:220px">权限类别&nbsp;：<u:select sname="category" stype="AUTH_TYPE" value=""  style="width:136px"/>
	 			</li>
	 			<li style="width:220px">权限名称&nbsp;：<input type="text" name="authName"   style="width: 128px"></input>
	 			</li>
	 			<li style="width:220px">权限代码&nbsp;：<input  type="text" name="authCode"   style="width: 128px"></input>
	 			</li>
	 		</ul>
	 		 
	 	</div>
	 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	 	
	 	</form>
	 </div>
	 
	 
</body>

</html>
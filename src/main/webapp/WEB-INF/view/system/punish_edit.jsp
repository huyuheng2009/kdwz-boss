<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript">
$(function(){
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var  id = $("input[name='id']").val();
		 var  punishText = $("input[name='punishText']").val();
		 if(punishText.length<2){
			 alert('请输入违规事项');
			 return false;
		 }
		 var ruleText = $('input[name=ruleText]').val();
		 if(ruleText.length<2){
			 alert('请输入处罚标准');
			 return false;
		 }
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/system/punish_save",//要访问的后台地址
	            data: {'id':id,'punishText':punishText,'ruleText':ruleText},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==0){
	            		alert('数据有误');
	            	}else if(msg==1){
	            		alert('保存成功');
	            		api.reload();
						api.close();
	            	}else{
	            		alert('数据有误');
	            	}
	            }
		 });
	 });
	
}); 


 
</script>
</head>
<body>
	<div class="content">
		 	<div class="item">
		 		<input  type="hidden" id="id" name="id" value="${systemPunish.id }"/>
	 			<ul style="padding-top:30px;">
		 			<li style="width:450px">违规事项：
		 				<input  maxlength="200" type="text" name="punishText" value="${systemPunish.punishText }" style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px">处罚标准：
		 				<input  maxlength="200" type="text" name="ruleText" value="${systemPunish.ruleText }" style="width: 300px"></input>
		 			</li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>
</html>
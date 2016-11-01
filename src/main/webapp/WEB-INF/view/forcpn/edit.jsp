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
		 var  cpnName = $("input[name='cpnName']").val();
		 if(cpnName.length<2){
			 alert('请输入转出公司');
			 return false;
		 }
		 var note = $('input[name=note]').val();
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/forcpn/save",//要访问的后台地址
	            data: {'id':id,'cpnName':cpnName,'note':note},//要发送的数据
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
		 		<input  type="hidden" id="id" name="id" value="${forCpn.id }"/>
	 			<ul style="padding-top:30px;">
		 			<li style="width:450px">转出公司：
		 				<input  maxlength="50" type="text" name="cpnName" value="${forCpn.cpnName }" style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px">备&#12288;&#12288;注：
		 				<input  maxlength="30" type="text" name="note" value="${forCpn.note }" style="width: 300px"></input>
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
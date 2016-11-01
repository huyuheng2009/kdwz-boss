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
		 var appCode = $('input[name=appCode]').val();
		 var appName = $('input[name=appName]').val();
		 var bname = $('input[name=bname]').val();
		 var id = $('input[name=id]').val();
		 if(appName.length<2){
			 alert('请输入产品名称');
			 return false;
		 }
		 if(bname.length<2){
			 alert('请输入产品别名');
			 return false;
		 }
		 var status = $('select[name=status]').val();
		 if(status==''){
			 alert('请选择状态');
			 return false;
		 }
		 
		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	           url: "${ctx }/client/psave",//要访问的后台地址
	            data: {'id':id,'appCode':appCode,'appName':appName,'status':status,'bname':bname},//要发送的数据
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
		 		<input  type="hidden" id="id" name="id" value="${appProduct.id }"/>
	 			<ul  style="padding-top:20px;padding-left: 20px;">
	 				<li style="width:220px">产品代码：
		 				<input disabled="disabled" maxlength="30" type="text" name="appCode" value="${appProduct.appCode }" style="width: 128px"></input>
		 			</li>
		 			<li style="width:220px">启用状态：
		 			    <u:select sname="status" stype="SWITCH" value="${appProduct.status }" />
		 			</li>
		 			<li style="width:220px">中文名称：
		 				<input maxlength="20"   type="text" name="appName" value="${appProduct.appName }" style="width: 128px"></input>
		 			</li>
		 			<li style="width:220px">英文别名：
		 				<input maxlength="20"   type="text" name="bname" value="${appProduct.bname }" style="width: 128px"></input>
		 			</li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>

</html>
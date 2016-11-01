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
		var  ids = $('input[name=ids]').val();
		var examineStatus = $('input[name=examineStatus]:checked').val();
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/order/examine_save",//要访问的后台地址
	            /* secureuri:false,
	            fileElementId:'file', */
	            data: {'ids':ids,'examineStatus':examineStatus},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功');
	            		//api.reload();
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
		 	<div class="item">
		 		<input  type="hidden" id="ids" name="ids" value="${params.ids }"/>
	 			<ul style="padding-top:30px;">
		 			<li style="width:450px"><!-- 快递公司编号： -->
		 				<input   maxlength="15" type="radio" name="examineStatus" value="PASS"  checked="checked"  style="width: 128px">通过</input>
		 				<input   maxlength="15" type="radio" name="examineStatus" value="NONE"  style="width: 128px">不通过</input>
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
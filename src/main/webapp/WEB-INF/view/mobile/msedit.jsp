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
		 var  name = $("input[name='name']").val();
		 var  discount = $("input[name='discount']").val();
		 var  note = $("input[name='note']").val();
		 
		 if(name.length<2){
			 alert('请输入优惠类型');
			 return false;
		 }
		 var r=/^\d{1,3}$/i ;
		 if(!r.test(discount)||discount>100){
			 alert('请输入正确的折扣，0-100');
			 return false;
		 }
		 var id = $('input[name=id]').val();
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/mobile/mssave",//要访问的后台地址
	            /* secureuri:false,
	            fileElementId:'file', */
	            data: {'id':id,'name':name,'discount':discount,'note':note},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
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
		 		<input  type="hidden" id="id" name="id" value="${monthSettleType.id }"/>
	 			<ul style="padding-top:30px;">
		 			<li style="width:450px">优惠类型：
		 				<input  maxlength="20" type="text" name="name" value="${monthSettleType.name }" placeholder="" style="width: 250px"></input>
		 			</li>
		 			<li style="width:450px">应收款为本月产生款项的：<input  maxlength="20" type="text" name="discount" value="${monthSettleType.discount }" style="width: 60px"></input>%(百分之)
		 			</li>
		 			<li style="width:450px">&#12288;&#12288;备注：
		 				<input  maxlength="20" type="text" name="note" value="${monthSettleType.note }" style="width: 250px"></input>
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
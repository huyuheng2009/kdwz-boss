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
		 var  name = $.trim($("input[name='name']").val());
		 var  discount = $.trim($("input[name='discount']").val());
		 var  minVal = $.trim($("input[name='minVal']").val());
		 var  maxVal = $.trim($("input[name='maxVal']").val());
		 var  note = $("input[name='note']").val();
		 
		 if(name.length<2){
			 alert('请输入优惠类型');
			 return false;
		 }
		 var r=/^\d{2}$/i ;
		 if(!r.test(discount)){
			 alert('请输入正确的折扣，两位数字');
			 return false;
		 }
		 
		 var dr = /^\d+$/i ;
		 if(!dr.test(minVal)){
			 alert('请输入正确的充值最低金额');
			 return false;
		 }
		 if(!dr.test(maxVal)){
			 alert('请输入正确的充值最高金额');
			 return false;
		 }
		 if(minVal-maxVal>0){
			 alert('充值最高金额必须大于充值最低金额');
			 return false;
		 }
		 
		 
		 var id = $('input[name=id]').val();
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/disuser/tsave",//要访问的后台地址
	            /* secureuri:false,
	            fileElementId:'file', */
	            data: {'id':id,'name':name,'discount':discount,'note':note,'minVal':minVal,'maxVal':maxVal},//要发送的数据
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
		 		<input  type="hidden" id="id" name="id" value="${discountType.id }"/>
	 			<ul style="padding-top:30px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 			<li style="width:450px"><span  class="lleft">优惠类型：</span>
		 				<input  maxlength="20" type="text" name="name" value="${discountType.name }" placeholder="" style="width: 250px"></input>
		 			</li>
		 			<li style="width:450px"><span  class="lleft">实付金额：</span>
		 			<input  maxlength="20" type="text" name="discount" value="${discountType.discount }" style="width: 250px"></input>%优惠。
		 			</li>
		 		 </ul>
					<ul style="padding-top:20px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
					<li style="width:450px"><span  class="lleft">充值金额适配</span>
		 			</li>
		 			
		 			<li style="width:450px"><span  class="lleft">充值金额：</span>
		 			<input  maxlength="20" type="text" name="minVal" value="${discountType.minVal }" style="width: 100px"/>
		 			≤    $   &lt; 
		 			<input  maxlength="20" type="text" name="maxVal" value="${discountType.maxVal }" style="width: 100px"/>
		 			</li>
					</ul>
					
				<ul style="padding-top:20px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;width: 470px;">
		 			
		 			<li style="width:450px"><span  class="lleft">备注：</span>
		 				<input  maxlength="20" type="text" name="note" value="${discountType.note }" style="width: 250px"></input>
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
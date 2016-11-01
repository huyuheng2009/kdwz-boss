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
		 var ids = "";
		 var items = $('[name = "pid"]:checkbox:checked');
		 for (var i = 0; i < items.length; i++) {
		      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
		      ids = (ids + items.get(i).value) + (((i + 1)== items.length) ? '':','); 
		 }
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/lgc/lp_save",//要访问的后台地址
	            data: {'lgcNo':$('#lgcNo').val(),'ids':ids},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功');
	            		 var api = frameElement.api, W = api.opener;
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
	 <form action="${ctx }/lgc/lp_save" method="post">
	 	<div class="item">
	 		 <input  type="hidden" id="lgcNo" name="lgcNo" value="${params.lgcNo }"/>
	 			<ul style="padding-top:20px;padding-left: 20px;">
	 			<li>请选择支付方式</li>
	 			<c:forEach items="${payTypeList}" var="item" varStatus="status">
	 			<li style="width:50%;overflow: hidden;height:20px;">
	    			<label><input type="checkbox" style="width: 20px" name="pid"  value="${item.id}" <c:out value="${item.su eq 'Y'?'checked':'' }"/> />${item.pay_name }</label>
				   </li>
    		</c:forEach>
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
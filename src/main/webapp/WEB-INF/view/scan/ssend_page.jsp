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
		 orderNo = $('input[name=orderNo]').val();
		 substationNo = $('select[name=substationNo]').val();
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/scan/ssend",//要访问的后台地址
	            data: {'orderNo':orderNo,'substationNo':substationNo},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('发送成功');
	            		 location.reload();
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
	 		<ul style="padding-top:20px">
	 		   <input type="hidden" name="orderNo" value="${params['orderNo']}"/>
		 		<li style="width:540px">发送到下一站：<select id="substationNo" name="substationNo" style="width: 400px">
							<c:forEach items="${substationList}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
					</select>
				</li>
	 		</ul>
	 	</div>
	 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="发送" />
	 	</div>
	 </div>
</body>

</html>
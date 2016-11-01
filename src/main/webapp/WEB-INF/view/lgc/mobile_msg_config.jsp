<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<style>
.item_content li {width: 95%;padding-left: 30px;margin: 20px 10px;}
.item_content1 li {padding:5px;}

</style>
<script type="text/javascript">
$(function(){
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		     var TAKE_SEND_MSG =$("input[name=TAKE_SEND_MSG]:checked").val();
		     var SEND_SEND_MSG =$("input[name=SEND_SEND_MSG]:checked").val();
			 $.ajax({
				 type: "get",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据
		            url: "require_list_save",//要访问的后台地址
		            data: {'SEND_SEND_MSG':SEND_SEND_MSG,'TAKE_SEND_MSG':TAKE_SEND_MSG,'code':'MOBILEMSGCONFIG'},//要发送的数据
		            success: function(msg){//msg为返回的数据，在这里做数据绑定
						if(msg==1){
							alert('保存成功','',function(){
								api.reload();
								api.close();
							});
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
	 	<div class="item_content">
	 		<ul>
	 				<li>收件人短信通知:
	 				<label><input type="radio" name="TAKE_SEND_MSG" value="1" <c:out value="${map.TAKE_SEND_MSG==1?' checked':'' }"></c:out>/>是</label>&nbsp;&nbsp;
	 				<label><input type="radio" name="TAKE_SEND_MSG" value="0" <c:out value="${map.TAKE_SEND_MSG==0?' checked':'' }"></c:out>/>否</label>
	 		        </li>
	 		        
	 		        <li>寄件人短信通知:
	 				<label><input type="radio" name="SEND_SEND_MSG" value="1" <c:out value="${map.SEND_SEND_MSG==1?' checked':'' }"></c:out>/>是</label>&nbsp;&nbsp;
	 				<label><input type="radio" name="SEND_SEND_MSG" value="0" <c:out value="${map.SEND_SEND_MSG==0?' checked':'' }"></c:out>/>否</label>
	 		        </li>
	 		</ul>
	 	</div>

	 	<div class="operator"  style="padding-top: 30px;padding-left: 30px;">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	 </div>
</body>

</html>
<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<style>
textarea {
border: 1px solid #cdcdcd;
overflow: auto;
font-size: 12px;
line-height: 22px;
padding: 3px 5px 0;
border-radius: 2px;
-moz-border-radius: 2px;


height: 200px;
max-height: 200px;
min-height: 200px;
width: 350px;
max-width: 350px;
min-width: 350px;
}
</style>
<script type="text/javascript">
$(function(){

	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		  var id = $('input[name=id]').val();
		  var sarea = $("#sarea").val();
		  if(sarea.length<2){
				 alert('请输入收派范围');
				 return false;
			 }
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	           url: "${ctx }/lgc/csarea_save",//要访问的后台地址
	            data: {'id':id,'sarea':sarea},//要发送的数据
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
		 		<input  type="hidden" id="id" name="id" value="${courier.id }"/>
	 			<ul  style="padding-top:20px;padding-left: 20px;">
		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;快递员：</span>
		 				<input  maxlength="8" type="text" name="realName" value="${courier.realName }" disabled="disabled" style="width: 350px"></input>
		 			</li>
                   <li style="width:570px"><span  class="lleft">&nbsp;&nbsp;联系电话：</span>
		 				<input  maxlength="15" type="text" name="userName" value="${courier.userName }" disabled="disabled" style="width: 350px"></input>
		 			</li>
		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;收派范围：</span>
		 				  <textarea name="sarea" id="sarea"  placeholder="请输入收派范围">${courier.sarea}</textarea>
		 			</li>
                </li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px;padding-left: 200px;">
	 		<input class="button input_text  big blue_flat" type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>

 <script>
  $(function() {
	  
  
  });
  </script>

</html>
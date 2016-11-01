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
		 var name = $.trim($('input[name=name]').val());
         $('input[name=name]').val(name);
		 if(name.length<2){
			 alert('请输入区域名称');
			 return false;
		 }
       
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/dict/asave",//要访问的后台地址
	            data: {'id':$('#id').val(),'name':name,'level':$('#level').val(),'parentId':$('#parentId').val()},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==0){
	            		alert('数据有误');
	            	}
	            	if(msg==1){
	            		alert('保存成功');
	            		api.reload();
						api.close();
	            	}
	            }
		 });
	 });
	
}); 
 
</script>
</head>
<body>
	 <div class="content">
	 <form action="${ctx }/dict/asave" method="post">
	 	<div class="item">
	 		 <input  type="hidden" id="id" name="id" value="${params.id}"/>
	 		 <input  type="hidden" id="parentId" name="parentId" value="${params.parentId}"/>
	 		  <input  type="hidden" id="level" name="level" value="${params.level}"/>
	 		<ul style="padding-top:20px">
	 		    <li style="width:220px">上级名称：<u:city cityId="${params.parentId}"></u:city></li>
	 			<li style="width:220px">区域名称：
	 			<input  maxlength="15" type="text" name="name" value="${params.name}" style="width: 128px"></input>
	 			</li>
	 		
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
<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>

<style>
.item_content li {width: 160px;padding-left: 30px;margin: 6px;}
.item_content1 li {padding:5px;}

</style>
<script type="text/javascript">
$(function(){
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var tid = $('#tid').val();
		 var ids = '' ;
			$('input[name=ids]:checked').each(function(){
               ids+= $(this).val()+',';
			});
			if(ids.length>0){
        		ids = ids.substring(0, ids.length-1) ;
        	}	
		var submit = true ;		
		
		if(submit){
			 $.ajax({
				 type: "post",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据
		            url: "sudo_save",//要访问的后台地址
		            data: {'tid':tid,'ids':ids},//要发送的数据
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
		}	
		
	 });
	
}); 
 
</script>
</head>
<body>
	 <div class="content">
	 	<input type="hidden" name="tid" id="tid" value="${params.ids}"/>
	 	<div class="item_content">
	 		<ul>
	 		 <c:forEach items="${suList}" var="item" varStatus="status">
	 		   <li><label><input type="checkbox"  name="ids"  value="${item.id}"  />${item.authName}</label></li> 
	 	     </c:forEach>
	 		</ul>
	 		
	 	</div>

	 	<div class="operator"  style="padding-top: 30px;padding-left: 30px;">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	 </div>
</body>

</html>
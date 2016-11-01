<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<%@ include file="/header.jsp"%>
<style>
*{padding:0;margin:0;}
.lleft{width: 115px;text-align: right;float: left;}
li {padding: 4px 0px;}
</style>
<script type="text/javascript">
$(function(){


	
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var id = $('input[name=id]').val();
		 var huifang_text = $.trim($("#huifang_text").val());
		 if(huifang_text.length<1||huifang_text.length>30){
			 alert("请输入回访内容,30个字符以内") ;
			 return false ;
		 }
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/customer/hfsave",//要访问的后台地址
	            data: {'id':id,'huifang_text':huifang_text},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功',"",function(){
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
		 	<div class="item">
		 		<input  type="hidden" id="id" name="id" value="${params.id }"/>
	 			<ul style="padding-top:30px;padding-left: 5px;float: left;">
		 		
                   <li style="width:320px"><span  class="lleft"><span style="color: red;">&nbsp;</span>客户号：</span>
		 				<input  maxlength="20" type="text" name="customer_no" disabled="disabled" value="${params.customer_no }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">&nbsp;</span>公司简称：</span>
		 				<input  maxlength="20" type="text" name="cpn_sname" disabled="disabled" value="${params.cpn_sname }" style="width: 160px"></input>
		 			</li>

                    <li style="width:670px"><span  class="lleft">客户回访：</span>
                    <textarea name="huifang_text" id="huifang_text" rows="" cols="" placeholder="输入30个字段以内" style="margin: 0px; height: 95px; width: 488px;"></textarea>
		 			</li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" style="margin-left: 150px;" type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>
</html>
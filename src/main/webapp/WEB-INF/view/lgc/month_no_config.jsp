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
		 var NO_FIX = $.trim($('input[name=NO_FIX]').val());
		 var NO_LENGTH = $.trim($('input[name=NO_LENGTH]').val());
		 var r1 = /^[0-9a-zA-Z]+$/ ;
		  if(!r1.test(NO_FIX)){
			  alert("请输入正确的前缀") ;
			  return false;
		   }
		 var r = /^[0-9]{1,3}$/ ;
		  if(!r.test(NO_LENGTH)){
			  alert("请输入正确月结号长度，1-3数字") ;
			  return false;
		   }
		 var LX_LENGTH = $.trim($('input[name=LX_LENGTH]').val());
		 if(!r.test(LX_LENGTH)){
			  alert("请输入正确联想位数，1-3数字") ;
			  return false;
		   }
			 $.ajax({
				 type: "get",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据
		            url: "require_list_save",//要访问的后台地址
		            data: {'NO_FIX':NO_FIX,'NO_LENGTH':NO_LENGTH,'LX_LENGTH':LX_LENGTH,'code':'MONTH_NO'},//要发送的数据
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
	 		   <li>
	 		      月结号前缀：<input type="text" <%--<c:out value="${!empty configMap.NO_FIX?'disabled':'' }"/> --%>  name="NO_FIX" value="${configMap.NO_FIX }" style="width: 200px;"/>
              </li> 
	 		
	 		  <li> <!--  value="${configMap.NO_LENGTH }" value="${configMap.LX_LENGTH }"   -->
	 		   月结号长度： 前缀+<input type="text" name="NO_LENGTH" value="5" disabled="disabled" style="width: 163px;"/>
              </li> 
              
              <li>
	 		   &#12288;联想位数： <input type="text" name="LX_LENGTH" value="5" disabled="disabled" style="width: 200px;"/> app收件联想位数暂定5位 
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
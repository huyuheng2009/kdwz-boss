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
		 var MIN_LENGTH = $.trim($('input[name=MIN_LENGTH]').val());
		 var r = /^[0-9]{1,3}$/ ;
		  if(!r.test(MIN_LENGTH)){
			  alert("请输入正确运单号最小长度，1-3数字") ;
			  return false;
		   }
		 var MAX_LENGTH = $.trim($('input[name=MAX_LENGTH]').val());
		 if(!r.test(MAX_LENGTH)){
			  alert("请输入正确运单号最大长度，1-3数字") ;
			  return false;
		   }
		 var CONSTATUTE = $.trim($('select[name=CONSTATUTE]').val());
			 $.ajax({
				 type: "get",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据
		            url: "require_list_save",//要访问的后台地址
		            data: {'MIN_LENGTH':MIN_LENGTH,'MAX_LENGTH':MAX_LENGTH,'CONSTATUTE':CONSTATUTE,'code':'LGC_ORDER_NO'},//要发送的数据
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
	 		      运单号长度：<input type="text" name="MIN_LENGTH" value="${configMap.MIN_LENGTH }" style="width: 70px;"/>≤长度≤ <input type="text" name="MAX_LENGTH" value="${configMap.MAX_LENGTH}" style="width: 70px;"/>
              </li> 
	 		
	 		  <li>
	 		   运单号构成：   <select name="CONSTATUTE" style="width: 197px;">
	 		            <option value="NO_OR_EN" ${configMap['CONSTATUTE'] eq 'NO_OR_EN'?'selected':''}>数字+字母</option>
	 		            <option value="NO_ONLY" ${configMap['CONSTATUTE'] eq 'NO_ONLY'?'selected':''}>纯数字</option>
	 		   </select>
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
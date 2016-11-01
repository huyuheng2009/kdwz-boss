<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<style>
*{padding:0;margin:0;}
.lleft{width: 120px;text-align: right;float: left;}
</style>
<script type="text/javascript">
$(function(){
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var id = $('input[name=id]').val();
		 var reasonNo = $.trim($('input[name=reasonNo]').val());
		 var context = $.trim($('input[name=context]').val());
		// var dealed = $('select[name=dealed]').val();
		 var note = $.trim($('input[name=note]').val());
		 var p2 = /^[0-9]{1,6}$/ ;
		 if(!p2.test(reasonNo)){
			 alert('请输入正确的问题编号');
			 return false;
		 }
		 if(context.length<2){
			 alert('请输入问题原因');
			 return false;
		 }
		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/porder/rsave",//要访问的后台地址
	            data: {'id':id,'reasonNo':reasonNo,'context':context,/*'dealed':dealed,*/'note':note},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功');
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
		 	<div class="item">
		 		<input  type="hidden" id="id" name="id" value="${proOrderReason.id }"/>
	 			<ul style="padding-top:30px;padding-left: 25px;">
	 			
	 			<li style="width:450px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>问题件编号：</span>
		 				<input  maxlength="20" type="text" name="reasonNo"  placeholder="请输入问题件编号"  value="${proOrderReason.reason_no }" style="width: 300px"></input>
		 	  </li>
		 	  
		 	<li style="width:450px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>问题件原因：</span>
		 				<input  maxlength="20" type="text" name="context"  placeholder="请输入问题件原因"  value="${proOrderReason.context }" style="width: 300px"></input>
		 </li>
		 <%--
		 
		 	<li style="width:450px"><span  class="lleft">是否需要客服处理：</span>
		 				<select name="dealed" style="width: 110px;">
                               <option value="Y" ${proOrderReason.dealed eq 'Y'?'selected':''}>是</option>
                               <option value="N" ${proOrderReason.dealed ne 'Y'?'selected':''}>否</option>
                          </select>
		 			</li>
		  --%>
		 			
		 			 
                    <li style="width:450px"><span  class="lleft">备注：</span>
		 				<input  maxlength="20" type="text" name="note" value="${proOrderReason.note }" style="width: 300px"></input>
		 			</li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" style="margin-left: 150px;" type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>
</html>
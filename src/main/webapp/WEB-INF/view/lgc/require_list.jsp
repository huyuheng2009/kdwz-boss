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
		 var ids = '0' ;
			$('input[name=ids]:checked').each(function(){
               ids+= ','+$(this).val();
			});
		var freight_type = $('input[name=freight_type]:checked').val() ;	
		var weight_type = $('input[name=weight_type]:checked').val() ;	
		var submit = true ;
		if('AUTO'==freight_type){
			if(!$("#sendArea").prop('checked')){
				alert('寄件人区域必须选择必填');
				submit = false ;
				return ;
			}
			if(!$("#sendAddr").prop('checked')){
				alert('寄件人地址必须选择必填');
				submit = false ;
				return ;
			}
			if(!$("#revArea").prop('checked')){
				alert('收件人区域必须选择必填');
				submit = false ;
				return ;
			}
			if(!$("#revAddr").prop('checked')){
				alert('收件人地址必须选择必填');
				submit = false ;
				return ;
			}
		}
		if('NULL'==weight_type){
			var weight = $('input[name=weight]').val() ;
			var r = /^\d+(.[0-9]{1,2})?$/i ;
			if(!r.test(weight)){
				alert('请输入正确的默认重量');
				submit = false ;
				return ;
			}
				weight_type = 	weight ;	
		}
		
		if(submit){
			 $.ajax({
				 type: "get",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据
		            url: "require_list_save",//要访问的后台地址
		            data: {'tid':tid,'ids':ids,'freightType':freight_type,'weightType':weight_type,'code':'ORDER_TAKE'},//要发送的数据
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
	 	<div class="item_title" style="padding-top:8px">${params.describe}</div>
	 	<input type="hidden" name="tid" id="tid" value="${params.id}"/>
	 	<div class="item_content" style="width: 60%;float: left;">
	 		<ul>
	 		 <c:forEach items="${list}" var="item" varStatus="status">
	 		   <li><label><input type="checkbox" id="${item.name}" name="ids" <c:out value="${item.editable eq 'N'?'disabled':''}"/> value="${item.id}" <c:out value="${item.required eq 'Y'?'checked':''}"/> />${item.describe}</label></li> 
	 	     </c:forEach>
	 		</ul>
	 		
	 	</div>
	 	
	 	 	<div class="item_content1" style="width: 30%;float: left;">
	 		
	 		 <c:forEach items="${rlist}" var="item" varStatus="status">
	 		 <c:if test="${item.name eq 'freight_type'}">
	 		    <ul style="border: 1px;border-style: solid;border-color:#D4CDCD;padding: 10px;margin-bottom: 10px;">
	 		   <li><label> <input type="radio"  name="freight_type"  value="NULL" <c:out value="${item.required eq 'NULL'?'checked':''}"/> />&nbsp;运费默认为空</label></li> 
	 		   <li><label><input type="radio"  name="freight_type"  value="LAST" <c:out value="${item.required eq 'LAST'?'checked':''}"/> />&nbsp;运费默认上次填写</label></li> 
	 		   <li><label><input type="radio"  name="freight_type"  value="AUTO" <c:out value="${item.required eq 'AUTO'?'checked':''}"/> />&nbsp;自动计算运费</label></li> 
	 	    	</ul>
	 	    </c:if>
	 	    
	 	    
	 	     <c:if test="${item.name eq 'weight_type'}">
	 		    <ul style="border: 1px;border-style: solid;border-color:#D4CDCD;padding: 10px;margin-bottom: 10px;">
	 		    <li><label><input type="radio"  name="weight_type"  value="NULL" <c:out value="${item.required ne 'LAST'?'checked':''}"/> />&nbsp;重量默认<input type="text" name="weight" 
	 		    value="<c:out value="${item.required ne 'LAST'?item.required:''}"/>" style="width:60px; "/> kg</label></li> 
	 		   <li><label><input type="radio"  name="weight_type"  value="LAST" <c:out value="${item.required eq 'LAST'?'checked':''}"/> />&nbsp;重量默认上次填写</label></li> 
	 	    	</ul>
	 	    </c:if>
	 	    	
	 	     </c:forEach>
	 	
	 	</div>
	 	
	 	<div class="operator"  style="padding-top: 30px;padding-left: 30px;">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	 </div>
</body>

</html>
<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script  type="text/javascript" src="/scripts/city_hp.js"></script> 
<script type="text/javascript">
$(function(){
	 var rareas = '${lgc1.defaultCity}'.split("-") ;
	   InitOption('rprovince','rcity','rarea',rareas[0],rareas[1]);
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var  name = $("input[name='name']").val();
		 if(name.length<2){
			 alert('请输入快递公司名称');
			 return false;
		 }
		 var lgcNo = $("input[name='lgcNo']").val();
		 var id = $('input[name=id]').val();

		 var rprovince = 	$('select[name=rprovince]').val() ;
		   var rcity = 	$('select[name=rcity]').val() ;
	        if(rprovince.length<1){
		    	alert("请区域省信息");
		    	$('select[name=rprovince]').focus();
				 return false;
			 }
		    if(rcity.length<1){
		   	alert("请区域市信息");
		    	$('select[name=rcity]').focus();
				 return false;
			 }
		 var defaultCity = rprovince + "-" + rcity; 
		 var contact = $('input[name=contact]').val();
		 var  website = $('input[name=website]').val();
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/lgc/lgcsave",//要访问的后台地址
	            data: {'id':id,'lgcNo':lgcNo,'name':name,'defaultCity':defaultCity,'contact':contact,'website':website},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==0){
	            		alert('数据有误');
	            	}else if(msg==1){
	            		alert('保存成功');
						api.close();
	            	}else{
	            		alert('数据有误');
	            	}
	            }
		 });
	 });
	
}); 

jQuery(function(){   
	  $("#upload").click(function(){     
	      //上传文件
	    $.ajaxFileUpload({
	        url:'${ctx }/lgc/lgclogo',
	        secureuri :false,
	        fileElementId :'logo',//file控件id
	        dataType : 'json',
	        success : function (data){
	           /*  if(typeof(data.error) != 'undefined'){
	                if(data.error != ''){
	                    alert(data.error);
	                }else{
	                    alert(data.r);
	                }
	            } */
	            alert(data);
	        }
	        
	});
	return false;
	  });
	});
 
</script>
</head>
<body>
	<div class="content">
		 	<div class="item">
		 		<input  type="hidden" id="id" name="id" value="${lgc1.id }"/>
	 			<ul style="padding-top:30px;">
		 			<li style="width:450px"><!-- 快递公司编号： -->
		 				<input  type="hidden" name="lgcNo" value="${lgc1.lgcNo}" style="width: 128px" ></input>
		 			</li>
		 			<li style="width:450px">快递公司名称：
		 				<input  maxlength="20" type="text" name="name" value="${lgc1.name }" style="width: 128px"></input>
		 			</li>
		 			<li style="width:450px">默认下单地址：
		 <select name="rprovince" style="width:30%;"  id="rprovince"  onchange="javascript:ChangeProvince('rcity',this.options[this.selectedIndex].value)"></select>
         <select name="rcity" style="width:30%;margin-left: 2%;" id="rcity"></select>
		  <select name="rarea"  style="display: none;" id="rarea" ></select>		
		 			</li>
		 			<li style="width:450px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系方式：
		 				<input  maxlength="20" type="text" name="contact" value="${lgc1.contact }" style="width: 128px"></input>
		 			</li>
		 			<li style="width:450px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;网址：
		 				<input  type="text" name="website" value="${lgc1.website }" style="width: 128px"></input>
		 			</li>
		 			<%-- <form:form id="lgc"  action="${ctx }/lgc/lgclogo" method="post" enctype="multipart/form-data">
		 				<li style="width:220px">logo：
		 					<input type="file" id="logo" name="logo"/>
		 					<input class="button input_text  big blue_flat"
						type="button" id="upload" name="upload" value="上传" /> 
		 				</li>
	 				</form:form> --%>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />&nbsp;&nbsp;&nbsp;&nbsp;重新登录后生效
	 	</div>
	</div>
</body>
</html>
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
<script type="text/javascript">
$(function(){

	  jQuery.ajax({
	      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	      dataType: "script",
	      cache: true
	}).done(function() {
		var data = tmjs.slist ;
		var availablesrcKey = [];
        $.each(data, function(i, item) {
        	var inner_no = "" ;
        	if(item.inner_no){inner_no=item.inner_no+',';}
        	availablesrcKey[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
        });
        val = '';
       $jqq("#substationNo").autocomplete(availablesrcKey, {
     		minChars: 0,
     		max: 12,
     		autoFill: true,
     		mustMatch: false,
     		matchContains: true,
     		scrollHeight: 220,
     		formatItem: function(data22, i, total) {
     			return data22[0];
     		}
     	});	 
	 });

	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var userName = $('input[name=userName]').val();
		/*  if(!checkPhone(userName,'N')){
			 alert('请输入正确的快递员用户名');
			 return false;
		 } */
	    
		 if(userName.length<2){
			 alert('请输入正确的登录账号');
			 return false;
		 } 
		 
		 var r = /^[0-9]{1,12}$/ ;
		  if(!r.test(userName)){
			  alert("请输入正确登录账号，1-12位数字") ;
			  return false;
		   }
		
		 var realName = $('input[name=realName]').val();
		 if(realName.length<2||realName.length>8){
			 alert('请输入正确的快递员姓名');
			 return false;
		 }
	/* 	 
		 var sprovince = 	$('select[name=sprovince]').val() ;
		   var scity = 	$('select[name=scity]').val() ;
		   var sarea = 	$('select[name=sarea]').val() ;
		   
		   if(sprovince.length<1){
		    	alert("请选择省");
		    	$('select[name=sprovince]').focus();
				 return false;
			 }
		    if(scity.length<1){
		    	alert("请选择市");
		    	$('select[name=scity]').focus();
				 return false;
			 }
		    if(sarea.length<1){
		    	alert("请选择区");
		    	$('select[name=sarea]').focus();
				 return false;
			 }
		 var sarea1 = sprovince + '-' +scity + '-' + sarea ; */
		 var innerNo = $('input[name=innerNo]').val();
		 if(innerNo.length<0){
			 alert('请输入内部员工编号');
			 return false;
		 }
		 var innerPhone = $('input[name=innerPhone]').val();
		 var idCard = $.trim($('input[name=idCard]').val());
		  if(idCard.length>0&&!reg[0].test(idCard)){
			  alert('请输入正确的快递员身份证号');
				 return false;
		  }
		 
		  var courierNo = $('input[name=courierNo]').val();
		  var id = $('input[name=id]').val();
		  var substationNo = $('input[name=substationNo]').val();
		 if(substationNo.length<5){
			 alert('分站编号错误');
			 return false;
		 }
		 if(substationNo.indexOf(')')>-1){
			 substationNo = substationNo.substring(0,substationNo.indexOf('(')); 
		 }
		 
		 var phone = $('input[name=phone]').val();
		 if(phone.length>1){
			 if(!checkPhone(phone,'Y')){
				 alert('请输入正确的联系电话');
				 return false;
			 }
		 }
		 
		 //queueName = $('input[name=queueName]').val();
		 var headImage = $('input[name=headImage]').val();
		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	           url: "${ctx }/lgc/csave",//要访问的后台地址
	            data: {'id':id,'userName':userName,'courierNo':courierNo,'substationNo':substationNo,'realName':realName,
	            	'phone':phone,'idCard':idCard,'headImage':headImage,'innerNo':innerNo,'innerPhone':innerPhone
	            	},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==5){
	            		alert('当前存在未收件或待派件，不允许修改分站编号');
	            	}else if(msg==4){
	            		alert('分站编号错误');
	            	}else if(msg==3){
	            		alert('用户名已存在');
	            	}else if(msg==1){
	            		 $.ajax({
	            			 type: "post",//使用get方法访问后台
	            	          dataType: "json",//返回json格式的数据
	            	          url: "${ctx }/updata",//要访问的后台地址
	            	          data: {},//要发送的数据
	            	           success: function(data){//msg为返回的数据，在这里做数据绑定
	            	           if(data.su==1){
	            	        	    jQuery.ajax({
	            	        	    	  type: "get",
	            	        		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	            	        		      dataType: "script",
	            	        		      cache: false
	            	        		  }).done(function() {
	            	        		    
	            	        		 });
	            	                }
	            	           }
	            		 }); 
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
	 				<li style="width:570px"><!-- 快递员编号： -->
		 				<input disabled="disabled" maxlength="30" type="hidden" name="courierNo" value="${courier.courierNo }" style="width: 128px"></input>
		 			</li>
		 			
		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;所属分站：</span>
		 				<input  maxlength="30" type="text" placeholder="请输入分站关键字" id="substationNo" name="substationNo" value="${courier.substationNo }" style="width: 300px"></input>
		 			</li>
		 			
		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;登陆账号：</span>
		 				<input  maxlength="15" type="text" name="userName" value="${courier.userName }" <c:out value="${!empty courier.userName?'disabled':'' }"/> style="width: 128px"></input>
		 			<span style="width:200px;color: gray;font-size: 10px;overflow: hidden;"></span>
		 			</li>
		 			
		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;用户姓名：</span>
		 				<input  maxlength="8" type="text" name="realName" value="${courier.realName }" style="width: 128px"></input>
		 			</li>

		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;内部员工编号：</span>
		 				<input  maxlength="8" type="text" name="innerNo" value="${courier.innerNo }" style="width: 128px"></input>
		 			</li>
		 			
		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;内部联系号码：</span>
		 				<input  maxlength="8" type="text" name="innerPhone" value="${courier.innerPhone }" style="width: 128px"></input>
		 			</li>
		 			
		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;身份证号：</span>
		 				<input  maxlength="18" type="text" name="idCard" value="${courier.idCard }" style="width: 300px"></input>
		 			</li>
		 			
		 			
		 			<li style="width:570px"><span  class="lleft">&nbsp;&nbsp;联系电话：</span>
		 				<input maxlength="20"   type="text" name="phone" value="${courier.phone }" style="width: 128px"></input>
		 			</li>
		 			<%-- <li style="width:420px">&nbsp;&nbsp;队列名称：
		 				<input  type="text" name="queueName" value="${courier.queueName}" style="width: 128px"></input>
		 			</li> --%>
		 			<%-- <li style="width:420px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;头像：
		 				<input  type="text" name="headImage" value="${courier.headImage}" style="width: 128px"></input> 
		 			</li> --%>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>

</html>